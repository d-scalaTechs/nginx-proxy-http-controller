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
  def name = column[String]("name")
  def description = column[String]("description")
  def entrance = column[String]("entrance")
  def systemType= column[Int]("system_type")
  override def * =
    (id, name, description, entrance,systemType) <>(GraySystem.tupled, GraySystem.unapply)
}


class GraySystems @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {

  val graySystems = TableQuery[GraySystemTableDef]
  val grayConfigs = TableQuery[GrayConfigTableDef]

  def add(graySystem: GraySystem): Future[String] = {
    db.run(graySystems += graySystem).map(res => "graySystem successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }
  def delete(id: Long): Future[Int] = {
    db.run(graySystems.filter(_.id === id).delete)
  }

  def update(graySystem: GraySystem): Future[Int] = {
    println(graySystem)
    db.run(graySystems.filter(_.id === graySystem.id).update(graySystem))
  }
  def get(id: Long): Future[GraySystem] = {
    db.run(graySystems.filter(_.id === id).result.head)
  }

  def listAll: Future[Seq[GraySystem]] = {
    db.run(graySystems.result)
  }

  def list(id: Long): Future[Seq[GraySystem]] = {
    db.run(graySystems
      .join(grayConfigs)
      .on((t1,t2) => t1.id === t2.targetId )
//      .filter{case (t1, t2) => t2.system === id }
      .map{ case (t1, t2) => t1 }.result )
  }

  def detail(id:Long): Future[Seq[GrayConfig]]= {
       db.run(grayConfigs.filter(_.targetId === id).result)
  }
}