package auth

import java.util.UUID

import com.mohiva.play.silhouette.api.LoginInfo

import scala.concurrent.{ExecutionContext, Future}

/**
 * Give access to the user object.
 */
trait UserDao {

  /**
   * Finds a user by its login info.
   *
   * @param loginInfo The login info of the user to find.
   * @return The found user or None if no user for the given login info could be found.
   */
  def find(loginInfo: LoginInfo)(implicit ec: ExecutionContext): Future[Option[User]]

  /**
   * Finds a user by its user ID.
   *
   * @param userID The ID of the user to find.
   * @return The found user or None if no user for the given ID could be found.
   */
  def find(userID: UUID)(implicit ec: ExecutionContext): Future[Option[User]]

  /**
   * Saves a user.
   *
   * @param user The user to save.
   * @return The saved user.
   */
  def save(user: User)(implicit ec: ExecutionContext): Future[User]
}
