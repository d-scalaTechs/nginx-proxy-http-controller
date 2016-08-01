package controllers

import javax.inject._

import daos.NativeDao
import play.api.mvc._
import services.GrayServerService

import scala.concurrent.ExecutionContext.Implicits.global
/**
 *
 * @author Eric on 2016/7/21 15:55
 */
@Singleton
class HomeController @Inject()(graySystem: GrayServerService,nativeDao:NativeDao) extends Controller {

  def index = Action.async { implicit request =>
    graySystem.listAllGraySystems map { graySystems =>
      Ok(views.html.index.render(nativeDao.getSubServerName(),graySystems))
    }
  }
}
