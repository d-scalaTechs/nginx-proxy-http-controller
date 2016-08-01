package services

import javax.inject.Inject

import daos._
import models._

import scala.concurrent.Future
/**
 *
 * @author Eric on 2016/7/21 15:55
 */
class GrayServerService @Inject() (grayServers: GrayServers)  {

  def addGraySystem(graySystem: GrayServer): Future[String] = {
    grayServers.add(graySystem)
  }

  def deleteGraySystem(id: Long): Future[Int] = {
    grayServers.delete(id)
  }


  def updateGraySystem(graySystem: GrayServer): Future[Int] = {
    grayServers.update(graySystem)
  }

  def updateGrayServerStatus(id: Long,status:Int): Future[Int] = {
    grayServers.updateGrayServerStatus(id,status)
  }

  def getGraySystem(id: Long): Future[Option[GrayServer]] = {
    grayServers.get(id)
  }

  def listAllGraySystems: Future[Seq[GrayServer]] = {
    grayServers.listAll
  }
  def listAllGraySystemsByConf(id: Int): Future[Seq[GrayServer]] = {
    grayServers.list(id)
  }

  def getGraySystemDetail(id: Long):Future[Seq[GrayConfig]] = {
    grayServers.detail(id)
  }

  def buildRedisKeyAndValue: Future[Seq[(String,String,String,String)]] = {
    //    grayConfigs.getValuesByServerIdAndKeyFuture(serverId,key)
    grayServers.buildRedisKeyAndValue
  }

  def buildRedisKey: Future[Seq[(Int,String)]] = {
    grayServers.buildRedisKey
  }
}