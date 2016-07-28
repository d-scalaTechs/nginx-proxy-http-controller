package services

import javax.inject.Inject

import daos._
import models._

import scala.concurrent.Future
/**
 *
 * @author Eric on 2016/7/21 15:55
 */
class GrayConfigService @Inject() (grayConfigs: GrayConfigs)  {

  def addGrayConfig(greySystem: GrayConfig): Future[String] = {
    grayConfigs.add(greySystem)
  }

  def deleteGrayConfig(id: Long): Future[Int] = {
    grayConfigs.delete(id)
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

  def getRedisKeys:  Future[Seq[(Long,String)]]  = {
    grayConfigs.getRedisKeys
  }

  def getValuesByServerIdAndKey(serverId: Long,key:String): Future[Seq[String]] = {
    grayConfigs.getValuesByServerIdAndKeyFuture(serverId,key)
  }
}