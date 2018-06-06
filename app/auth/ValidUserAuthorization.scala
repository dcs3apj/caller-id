package auth

import com.mohiva.play.silhouette.api.Authorization
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import play.api.mvc.Request

import scala.concurrent.Future

class ValidUserAuthorization(validEmailAddresses: Seq[String]) extends Authorization[User, CookieAuthenticator] {
  override def isAuthorized[B](identity: User, authenticator: CookieAuthenticator)(implicit request: Request[B]): Future[Boolean] = {
    Future.successful(identity.email.exists(email => validEmailAddresses.contains(email)))
  }
}
