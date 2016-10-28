package services

import java.sql.Date
import javax.inject.Inject

import daos._
import models._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
/**
 *
 * @author Eric on 2016/7/21 15:55
 */
class GrayConfigService @Inject() (grayConfigs: GrayConfigs)  {

  def addGrayConfig(greySystem: GrayConfig): Future[String] = {
    Future{greySystem.value.split("\\s+").map{vale=>
        grayConfigs.add(models.GrayConfig(0, "staffName",vale,greySystem.systemId,new Date(System.currentTimeMillis())))
      }
      "ok"
    }
  }

  def deleteGrayConfig(id: Long): Future[Int] = {
    grayConfigs.delete(id)
  }

  def deleteBatchGrayConfig(ids: String): Future[Int] = {
    Future{
      val idInts: List[Int] = ids.split(",").map(el=>el.toInt).toList
      idInts.foreach{ id=>
        grayConfigs.delete(id)
      }
      idInts.length
    }
  }

  def updateGrayConfig(grayConfig: GrayConfig): Future[Int] = {
    grayConfigs.update(grayConfig)
  }

  def getGrayConfig(id: Long): Future[Option[GrayConfig]] = {
    grayConfigs.get(id)
  }

  def listAllGrayConfigs: Future[Seq[GrayConfig]] = {
    grayConfigs.listAll
  }


//  def getRedisKeys:  Future[Seq[(Long,String)]]  = {
//    grayConfigs.getRedisKeys
//  }
//
//  def getValuesByServerIdAndKey(serverId: Long,key:String): Future[Seq[String]] = {
//    grayConfigs.getValuesByServerIdAndKeyFuture(serverId,key)
//  }
 
}