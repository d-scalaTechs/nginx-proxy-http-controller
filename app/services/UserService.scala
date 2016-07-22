package services

import javax.inject.Inject

import daos._
import models._
import scala.concurrent.Future
/**
 *
 * @author Eric on 2016/7/21 15:55
 */
class UserService @Inject() (users: Users)  {

  def addUser(user: User): Future[String] = {
    users.add(user)
  }

  def deleteUser(id: Long): Future[Int] = {
    users.delete(id)
  }


  def updateUser(user: User): Future[Int] = {
    users.update(user)
  }

  def getUser(id: Long): Future[Option[User]] = {
    users.get(id)
  }

  def listAllUsers: Future[Seq[User]] = {
    users.listAll
  }
}