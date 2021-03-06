package dialogflow

import call.{Call, CallDao}
import cats.data.NonEmptyList

import scala.concurrent.{ExecutionContext, Future}

/**
  * The default implementation of [[DialogflowService]]
  * @param queryParameterService The service used to extract what has been asked for.
  * @param callToSpeechService The service used to translate calls to speech.
  * @param callDao The DAo used to find calls.
  * @param ec The execution context used to chain futures.
  */
class DialogflowServiceImpl(
                             queryParameterService: QueryParameterService,
                             callToSpeechService: CallToSpeechService,
                             callDao: CallDao)(implicit ec: ExecutionContext) extends DialogflowService {

  override def response(webhookRequest: WebhookRequest): Future[WebhookResponse] = {
    val parameters: QueryParameters = queryParameterService.createQueryParameters(webhookRequest)
    val eventualCalls: Future[Seq[Call]] =
      callDao.calls(parameters.max, parameters.since.map(_.toInstant), parameters.until.map(_.toInstant))
    eventualCalls.map { calls =>
      val messages: NonEmptyList[String] = calls.map(callToSpeechService.speak).toList match {
        case Nil => NonEmptyList.one("There have been no calls")
        case m :: ms => NonEmptyList(m, ms)
      }
      WebhookResponse(messages)
    }
  }
}
