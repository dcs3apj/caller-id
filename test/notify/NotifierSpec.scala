package notify

import java.time.Instant

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import akka.stream.scaladsl.{Keep, Source}
import call._
import cats.data.NonEmptyList
import modem.{Modem, ModemResponse}
import org.scalatest.{AsyncWordSpec, Matchers}
import play.api.inject.ApplicationLifecycle

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.Success

class NotifierSpec extends AsyncWordSpec with Matchers {

  implicit val actorSystem: ActorSystem = ActorSystem("notifierSpec")
  implicit val materializer: Materializer = ActorMaterializer()

  val now: Instant = Instant.parse("2018-05-20T15:39:00Z")
  val aCall: Call = Call(now, Withheld)

  val callService: CallService = new CallService {
    override def call(modemResponse: ModemResponse)(implicit ec: ExecutionContext): Future[Option[Call]] = Future.successful {
      modemResponse match {
        case modem.Withheld => Some(aCall)
        case _ => None
      }
    }
  }

  "The notifier" should {
    "notify when a call comes in" in {
      val eventualResponses = notify(modem.Withheld)
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
    val sink1: Call => Unit = call => promise1.complete(Success(call))
    val sink2: Call => Unit = call => promise2.complete(Success(call))
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
    val fakeApplicationLifecycle = new ApplicationLifecycle {
      override def addStopHook(hook: () => Future[_]): Unit = {}
      override def stop(): Future[_] = Future.successful({})
    }
    new Notifier(modem, callService, fakeApplicationLifecycle, NonEmptyList.of(sink1, sink2))(actorSystem, materializer, materializer.executionContext)
    for {
      result1 <- promise1.future
      result2 <- promise2.future
    } yield (result1, result2)
  }

}
