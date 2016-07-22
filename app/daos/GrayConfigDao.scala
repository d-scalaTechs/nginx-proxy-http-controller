package daos

/**
 *
 * @author Eric on 2016/7/21 19:21
 */
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import models._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class GrayConfigTableDef(tag: Tag) extends Table[models.GrayConfig](tag, "grey_config") {

  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  def system = column[Long]("system")
  def key = column[String]("last_name")
  def value = column[String]("mobile")
  def targetId = column[Int]("mobile")
  def updatedAt= column[Long]("column")
  override def * =
    (id, system, key, value,targetId,updatedAt) <>(GrayConfig.tupled, GrayConfig.unapply)
}


class GrayConfigs @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {

  val grayConfigs = TableQuery[GrayConfigTableDef]

  def add(graySystem: GrayConfig): Future[String] = {
    db.run(grayConfigs += graySystem).map(res => "greySystem successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }
  def delete(id: Long): Future[Int] = {
    db.run(grayConfigs.filter(_.id === id).delete)
  }

  def update(greySystem: GrayConfig): Future[Int] = {
    println(greySystem)
    db.run(grayConfigs.filter(_.id === greySystem.id).update(greySystem))
  }
  def get(id: Long): Future[Option[GrayConfig]] = {
    db.run(grayConfigs.filter(_.id === id).result.headOption)
  }

  def listAll: Future[Seq[GrayConfig]] = {
    db.run(grayConfigs.result)
  }
}
