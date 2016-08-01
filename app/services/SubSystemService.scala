package services

import javax.inject.Inject

import daos._
import models.SubSystem

import scala.concurrent.Future

/**
 *
 * @author Eric on 2016/7/21 15:55
 */
class SubSystemService @Inject() (subSystems: SubSystems)  {

  def listAll: Future[Seq[SubSystem]] = {
    subSystems.listAll
  }

  def getSubSystemName(subSystemId:Long): Future[Option[SubSystem]] = {
    subSystems.getSubSystemName(subSystemId)
  }
}