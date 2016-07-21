package controllers

/**
 *
 * @author Eric on 2016/7/21 15:55
 */
import javax.inject.{Singleton, Inject}

import play.api.db.Database
import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future

import models._
import pojos._
import play.api.mvc._
import scala.concurrent.Future
import services.UserService
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class UserController@Inject()(userService: UserService) extends Controller {

  def index = Action.async { implicit request =>
    userService.listAllUsers map { users =>
      Ok(views.html.user.render(UserForm.form, users,false))
    }
  }

  def addUser() = Action.async { implicit request =>
    UserForm.form.bindFromRequest.fold(
      errorForm => Future.successful(Ok(views.html.user.render(errorForm, Seq.empty[models.User],false))),
      data => {
        val newUser = models.User(0, data.firstName, data.lastName, data.mobile, data.email)
        userService.addUser(newUser).map(res =>
          Redirect("/user")
        )
      })
  }

  def deleteUser(id: Long) = Action.async { implicit request =>
    userService.deleteUser(id) map { res =>
      Redirect("/user")
    }
  }

  def updateUser = Action.async { implicit request =>
    UserForm.form.bindFromRequest.fold(
      errorForm => Future.successful(Ok(views.html.user.render(errorForm, Seq.empty[models.User],false))),
      data => {
        val newUser = models.User(0, data.firstName, data.lastName, data.mobile, data.email)
        userService.updateUser(newUser).map(res =>
          Redirect("/user")
        )
      })
  }


  def getUser(id: Long) = Action.async { implicit request =>
      userService.getUser(id) map { user => Ok(views.html.user.render(UserForm.form, Seq(user.get),true))
    }
  }
}
