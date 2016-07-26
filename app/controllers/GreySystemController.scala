package controllers

/**
 *
 * @author Eric on 2016/7/21 15:55
 */
import javax.inject.{Inject, Singleton}

import play.api.mvc.{Action, Controller}
import pojos._
import services.GraySystemService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class GreySystemController@Inject()(graySystem: GraySystemService) extends Controller {

  def index = Action.async { implicit request =>
    graySystem.listAllGraySystems map { graySystems =>
      Ok(views.html.graySystem.render(graySystems))
    }
  }

  def indexByConf(grayType:String) = Action.async { implicit request =>
    val systemId = if("web".equals(grayType)) 1l else if("oss".equals(grayType)){2l}else{0l}
    graySystem.listAllGraySystemsByConf(systemId) map { graySystems =>
      Ok(views.html.graySystem.render(graySystems))
    }
  }

  def detail(id: Long, name: String, description: String, entrance: String) = Action.async{ implicit request =>
       val newGraySystem = models.GraySystem(id, name,description, entrance)
        println(newGraySystem)
       graySystem.getGraySystemDetail(id) map {systemInfo=>
         Ok(views.html.graySystems.render(newGraySystem,systemInfo))
       }
  }


  def addGraySystem() = Action.async { implicit request =>
    GraySystemForm.form.bindFromRequest.fold(
      errorForm => Future.successful(Ok(views.html.graySystem.render(Seq.empty[models.GraySystem]))),
      data => {
        val newGraySystem = models.GraySystem(0, data.name, data.description, data.entrance)
        graySystem.addGraySystem(newGraySystem).map(res =>
          Redirect("/")
        )
      })
  }

  def deleteGraySystem(id: Long) = Action.async { implicit request =>
    graySystem.deleteGraySystem(id) map { res =>
      Redirect("/")
    }
  }

  def updateGraySystem(id: Long) = Action.async { implicit request =>
    GraySystemForm.form.bindFromRequest.fold(
      errorForm => Future.successful(Ok(views.html.graySystem.render(Seq.empty[models.GraySystem]))),
      data => {
        val newGraySystem = models.GraySystem(id, data.name, data.description, data.entrance)
        graySystem.updateGraySystem(newGraySystem).map(res =>
          Redirect("/graySystem")
        )
      })
  }


  def getGraySystem(id: Long) = Action.async { implicit request =>
      graySystem.getGraySystem(id) map { graySystem => Ok(views.html.graySystem.render(Seq(graySystem)))
    }
  }
}
