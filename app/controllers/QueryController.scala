package controllers

import javax.inject._

import play.api.mvc._
import services.GraySystemService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 *
 * @author Eric on 2016/7/21 15:55
 */
@Singleton
class QueryController @Inject()(graySystem: GraySystemService) extends Controller {

  def page = Action.async { implicit request =>
      Future.successful( Ok(views.html.query.render("page")))
  }
}
