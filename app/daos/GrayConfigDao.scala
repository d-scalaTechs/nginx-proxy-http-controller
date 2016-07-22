package daos

/**
 *
 * @author Eric on 2016/7/21 19:21
 */

import java.util.Date
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import models._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class GrayConfigTableDef(tag: Tag) extends Table[models.GrayConfig](tag, "grey_config") {
  implicit val JavaUtilDateMapper =
    MappedColumnType.base[java.util.Date, java.sql.Timestamp] (
      d => new java.sql.Timestamp(d.getTime),
      d => new java.util.Date(d.getTime))

  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  def system = column[Long]("system")
  def key = column[String]("key")
  def value = column[String]("value")
  def targetId = column[Long]("target_id")
  def updatedAt= column[Date]("updated_at")
  override def * =
    (id, system, key, value,targetId,updatedAt) <>(GrayConfig.tupled, GrayConfig.unapply)
}


class GrayConfigs @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {

  val grayConfigs = TableQuery[GrayConfigTableDef]

  def add(grayConfig: GrayConfig): Future[String] = {
    db.run(grayConfigs += grayConfig).map(res => "greySystem successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }
  def delete(id: Long): Future[Int] = {
    db.run(grayConfigs.filter(_.id === id).delete)
  }

  def update(grayConfig: GrayConfig): Future[Int] = {
    println(grayConfig)
    db.run(grayConfigs.filter(_.id === grayConfig.id).update(grayConfig))
  }
  def get(id: Long): Future[Option[GrayConfig]] = {
    db.run(grayConfigs.filter(_.id === id).result.headOption)
  }

  def listAll: Future[Seq[GrayConfig]] = {
    db.run(grayConfigs.result)
  }
}
