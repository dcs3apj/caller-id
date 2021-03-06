package notify

import java.time.Instant

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Keep, Source}
import akka.stream.{ActorMaterializer, Materializer}
import call._
import cats.data.NonEmptyList
import com.typesafe.config.ConfigFactory
import modem.{Modem, ModemResponse}
import notify.sinks.{CallSink, LoggingSink}
import org.scalatest.{AsyncWordSpec, Matchers}

import scala.concurrent.{Future, Promise}
import scala.util.Success

class NotifierSpec extends AsyncWordSpec with Matchers {

  implicit val actorSystem: ActorSystem = ActorSystem("notifierSpec", ConfigFactory.empty())
  implicit val materializer: Materializer = ActorMaterializer()

  val now: Long = Instant.parse("2018-05-20T15:39:00Z").toEpochMilli

  val aCall: Call = Call(now, Withheld)

  val callService: CallService = (modemResponse: ModemResponse) => Future.successful {
    modemResponse match {
      case modem.Withheld => Some(aCall)
      case _ => None
    }
  }

  "The notifier" should {
    "notify when a call comes in" in {
      val eventualResponses: Future[(Call, Call)] = notify(modem.Withheld)
      eventualResponses.map {
        case (response1, response2) =>
          response1 should ===(aCall)
          response2 should ===(aCall)
      }
    }
  }

  "The notifier" should {
    "not notify when a non-call response comes from the modem" in {
      val eventualResponses: Future[Option[(Call, Call)]] = notify(modem.Ok).map(Some(_))
      val awaitPromise: Promise[Option[(Call, Call)]] = Promise()
      Future {
        Thread.sleep(500)
        awaitPromise.complete(Success(None))
      }
      Future.firstCompletedOf(Seq(awaitPromise.future, eventualResponses)).map { maybeResponse =>
        maybeResponse should ===(None)
      }
    }
  }

  def notify(modemResponse: ModemResponse): Future[(Call, Call)] = {
    val promise1: Promise[Call] = Promise[Call]()
    val promise2: Promise[Call] = Promise[Call]()
    class PromiseCallSink(promise: Promise[Call]) extends CallSink {
      override def consume(call: Call): Future[_] = Future.successful(promise.complete(Success(call)))
    }
    val sink1 = new PromiseCallSink(promise1)
    val sink2 = new PromiseCallSink(promise2)
    //noinspection ConvertExpressionToSAM
    val modem: Modem = new Modem {
      override def responses(): Source[ModemResponse, Disconnect] = {
        val killSwitchSource: Source[ModemResponse, Disconnect] = Source.maybe[ModemResponse].mapMaterializedValue { promise =>
          new Disconnect {
            override def disconnect(): Unit = promise.success(None)
          }
        }
        Source.single(modemResponse).concatMat(killSwitchSource)(Keep.right)
      }
    }

    new Notifier(modem, callService, NonEmptyList.of(sink1, sink2, LoggingSink))(actorSystem, materializer, materializer.executionContext)
    for {
      result1 <- promise1.future
      result2 <- promise2.future
    } yield (result1, result2)
  }

}
