package services

import javax.inject.Inject

import daos._
import models._

import scala.concurrent.Future

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

  def getGraySystem(id: Long): Future[Option[GraySystem]] = {
    graySystems.get(id)
  }

  def listAllGraySystems: Future[Seq[GraySystem]] = {
    graySystems.listAll
  }
  def listAllGraySystemsByConf(id: Long): Future[Seq[GraySystem]] = {
    graySystems.list(id)
  }
}