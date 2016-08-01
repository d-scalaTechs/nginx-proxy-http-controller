package daos

/**
 * Created by eric on 16/7/30.
 */

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import models._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class SubSystemsTableDef(tag: Tag) extends Table[models.SubSystem](tag, "sub_systems") {

  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  def name = column[String]("name")

  override def * =
    (id, name) <>(SubSystem.tupled, SubSystem.unapply)
}

class SubSystems @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  val subSystems = TableQuery[SubSystemsTableDef]

  def listAll: Future[Seq[SubSystem]] = {
    db.run(subSystems.result)
  }

  def getSubSystemName(subSystemId:Long): Future[Option[SubSystem]] = {
    db.run(subSystems.filter(_.id===subSystemId).result.headOption)
  }
}