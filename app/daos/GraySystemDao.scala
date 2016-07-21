package daos

/**
 *
 * @author Eric on 2016/7/21 19:15
 */
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import models._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class GraySystemTableDef(tag: Tag) extends Table[models.GraySystem](tag, "grey_system") {

  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  def name = column[String]("first_name")
  def description = column[String]("last_name")
  def entrance = column[String]("mobile")
  override def * =
    (id, name, description, entrance) <>(GraySystem.tupled, GraySystem.unapply)
}


class GraySystems @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {

  val greySystems = TableQuery[GraySystemTableDef]

  def add(greySystem: GraySystem): Future[String] = {
    db.run(greySystems += greySystem).map(res => "greySystem successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }
  def delete(id: Long): Future[Int] = {
    db.run(greySystems.filter(_.id === id).delete)
  }

  def update(greySystem: GraySystem): Future[Int] = {
    println(greySystem)
    db.run(greySystems.filter(_.id === greySystem.id).update(greySystem))
  }
  def get(id: Long): Future[Option[GraySystem]] = {
    db.run(greySystems.filter(_.id === id).result.headOption)
  }

  def listAll: Future[Seq[GraySystem]] = {
    db.run(greySystems.result)
  }
}