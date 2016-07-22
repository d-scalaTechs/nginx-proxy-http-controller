package services

import javax.inject.Inject

import daos._
import models._

import scala.concurrent.Future
/**
 *
 * @author Eric on 2016/7/21 15:55
 */
class GraySystemService @Inject() (graySystems: GraySystems)  {

  def addGraySystem(graySystem: GraySystem): Future[String] = {
    graySystems.add(graySystem)
  }

  def deleteGraySystem(id: Long): Future[Int] = {
    graySystems.delete(id)
  }


  def updateGraySystem(graySystem: GraySystem): Future[Int] = {
    graySystems.update(graySystem)
  }

  def getGraySystem(id: Long): Future[GraySystem] = {
    graySystems.get(id)
  }

  def listAllGraySystems: Future[Seq[GraySystem]] = {
    graySystems.listAll
  }
  def listAllGraySystemsByConf(id: Long): Future[Seq[GraySystem]] = {
    graySystems.list(id)
  }

  def getGraySystemDetail(id: Long):Future[Seq[GrayConfig]] = {
     graySystems.detail(id)
  }
}