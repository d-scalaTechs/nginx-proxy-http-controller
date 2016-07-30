package controllers

/**
 *
 * @author Eric on 2016/7/21 15:55
 */
import javax.inject.{Inject, Singleton}

import daos.NativeDao
import play.api.mvc.{Action, Controller}
import pojos._
import services.GrayServerService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class GreyServerController@Inject()(graySystem: GrayServerService,nativeDao:NativeDao) extends Controller {

  def index = Action.async { implicit request =>
    graySystem.listAllGraySystems map { graySystems =>
      Ok(views.html.graySystems.render(0,graySystems))
    }
  }

  def indexByConf(grayType:String) = Action.async { implicit request =>
    val systemId = if("web".equals(grayType)) 1 else if("oss".equals(grayType)){2}else{0}
    graySystem.listAllGraySystemsByConf(systemId) map { graySystems =>
      Ok(views.html.graySystems.render(systemId,graySystems))
    }
  }

  def detail(id: Long) = Action.async{ implicit request =>
       val newGraySystem = nativeDao.getGrayServer(id)

       println(newGraySystem)
       graySystem.getGraySystemDetail(id) map {systemInfo=>
         Ok(views.html.grayServerConfigs.render(newGraySystem,systemInfo))
       }
  }


  def addGraySystem() = Action.async { implicit request =>
    GraySystemForm.form.bindFromRequest.fold(
      errorForm => Future.successful(Ok(views.html.graySystems.render(0,Seq.empty[models.GrayServer]))),
      data => {
        val newGraySystem = models.GrayServer(0, data.name, data.description, data.entrance,data.serverType,data.subSystemId,0)
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

  def updateGraySystem(id:Int) = Action.async { implicit request =>
//    val json = request.body.asJson
//    println("json: " + json)
//    Future.successful(Ok("{\"result\":0}"))
    GraySystemForm.form.bindFromRequest.fold(
      errorForm => Future.successful(Ok(views.html.graySystems.render(0,Seq.empty[models.GrayServer]))),
      data => {
        val newGraySystem = models.GrayServer(id, data.name, data.description, data.entrance,data.serverType,data.subSystemId,0)
        graySystem.updateGraySystem(newGraySystem).map(res =>
          Redirect("/graySystem")
        )
      })

  }

   def updateGrayServerStatus(id: Long,status:Int) = Action.async { implicit request =>
     graySystem.updateGrayServerStatus(id,status) map { res =>
       Redirect("/")
     }
  }

//  def getGraySystem(id: Long) = Action {
//       Ok(views.html.graySystem.render(Seq(graySystem.getGraySystem(id)))
//  }
}
