package controllers

/**
 *
 * @author Eric on 2016/7/21 15:55
 */

import java.sql.Date
import javax.inject.{Inject, Singleton}

import daos.NativeDao
import play.api.mvc.{Action, Controller}
import pojos._
import services.GrayConfigService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class GreyConfigController@Inject()(grayConfig: GrayConfigService,nativeDao:NativeDao) extends Controller {

  def index = Action.async { implicit request =>
    grayConfig.listAllGrayConfigs map { grayConfigs =>
      Ok(views.html.grayConfig.render(GrayConfigForm.form, grayConfigs,false))
    }
  }



  def validate(id:Long,key:String,value:String) = Action{
    val count = nativeDao.getUniqConf(id,key,value);
    if(count>0){
      Ok("{\"result\":-1}")
    }else{
      Ok("{\"result\":0}")
    }
  }

///graySystem/info/:id/:name/:des/:entrance
  def addGrayConfig() = Action.async { implicit request =>
  GrayConfigForm.form.bindFromRequest.fold(
      errorForm => Future.successful(
        Ok(views.html.grayConfig.render(errorForm, Seq.empty[models.GrayConfig],false))),
      data => {
        val newGrayConfig = models.GrayConfig(0, "staffName", data.value,data.systemId,new Date(System.currentTimeMillis()))
        grayConfig.addGrayConfig(newGrayConfig).map(res =>
          Redirect("/graySystem")
        )
      })
  }

  def deleteGrayConfig(id: Long) = Action.async { implicit request =>
    grayConfig.deleteGrayConfig(id) map { res =>
      Ok("{\"result\":0}")
    }
  }

  def updateGrayConfig(id: Long) = Action.async { implicit request =>
    GrayConfigForm.form.bindFromRequest.fold(
      errorForm => Future.successful(Ok(views.html.grayConfig.render(errorForm, Seq.empty[models.GrayConfig],false))),
      data => {
        val newGrayConfig = models.GrayConfig(id,"staffName", data.value,data.systemId,new Date(System.currentTimeMillis()))
        grayConfig.updateGrayConfig(newGrayConfig).map(res =>
          Ok("{result:true}")
        )
      })
  }


  def getGrayConfig(id: Long) = Action.async { implicit request =>
      grayConfig.getGrayConfig(id) map { grayConfig => Ok(views.html.grayConfig.render(GrayConfigForm.form, Seq(grayConfig.get),true))
    }
  }
}
