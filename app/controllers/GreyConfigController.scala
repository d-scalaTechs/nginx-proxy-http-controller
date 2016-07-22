package controllers

/**
 *
 * @author Eric on 2016/7/21 15:55
 */

import java.sql.Date
import javax.inject.{Inject, Singleton}

import play.api.mvc.{Action, Controller}
import pojos._
import services.GrayConfigService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class GreyConfigController@Inject()(grayConfig: GrayConfigService) extends Controller {

  def index = Action.async { implicit request =>
    grayConfig.listAllGrayConfigs map { grayConfigs =>
      Ok(views.html.grayConfig.render(GrayConfigForm.form, grayConfigs,false))
    }
  }

  def addGrayConfig() = Action.async { implicit request =>
    GrayConfigForm.form.bindFromRequest.fold(
      errorForm => Future.successful(Ok(views.html.grayConfig.render(errorForm, Seq.empty[models.GrayConfig],false))),
      data => {
        val newGrayConfig = models.GrayConfig(0, data.system, data.key, data.value,data.targetId,new Date(System.currentTimeMillis()))
        grayConfig.addGrayConfig(newGrayConfig).map(res =>
          Redirect("/grayConfig")
        )
      })
  }

  def deleteGrayConfig(id: Long) = Action.async { implicit request =>
    grayConfig.deleteGrayConfig(id) map { res =>
      Redirect("/grayConfig")
    }
  }

  def updateGrayConfig(id: Long) = Action.async { implicit request =>
    GrayConfigForm.form.bindFromRequest.fold(
      errorForm => Future.successful(Ok(views.html.grayConfig.render(errorForm, Seq.empty[models.GrayConfig],false))),
      data => {
        val newGrayConfig = models.GrayConfig(id, data.system, data.key, data.value,data.targetId,new Date(System.currentTimeMillis()))
        grayConfig.updateGrayConfig(newGrayConfig).map(res =>
          Redirect("/grayConfig")
        )
      })
  }


  def getGrayConfig(id: Long) = Action.async { implicit request =>
      grayConfig.getGrayConfig(id) map { grayConfig => Ok(views.html.grayConfig.render(GrayConfigForm.form, Seq(grayConfig.get),true))
    }
  }
}
