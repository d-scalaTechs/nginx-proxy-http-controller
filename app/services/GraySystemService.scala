package services

import javax.inject.Inject

import daos._
import models._

import scala.concurrent.Future

class GraySystemService @Inject() (greySystems: GraySystems)  {

  def addGraySystem(greySystem: GraySystem): Future[String] = {
    greySystems.add(greySystem)
  }

  def deleteGraySystem(id: Long): Future[Int] = {
    greySystems.delete(id)
  }


  def updateGraySystem(greySystem: GraySystem): Future[Int] = {
    greySystems.update(greySystem)
  }

  def getGraySystem(id: Long): Future[Option[GraySystem]] = {
    greySystems.get(id)
  }

  def listAllGraySystems: Future[Seq[GraySystem]] = {
    greySystems.listAll
  }
}