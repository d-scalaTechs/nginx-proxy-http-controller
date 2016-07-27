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

class GrayServersTableDef(tag: Tag) extends Table[models.GrayServer](tag, "grey_servers") {


  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  def name = column[String]("name")
  def description = column[String]("description")
  def entrance = column[String]("entrance")
  def systemType= column[Int]("server_type")
  def subSystem= column[String]("sub_system")
  def status= column[Int]("status")

  override def * =
    (id, name, description, entrance,systemType,subSystem,status) <>(GrayServer.tupled, GrayServer.unapply)
}


class GrayServers @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {

  val grayServers = TableQuery[GrayServersTableDef]
  val grayConfigs = TableQuery[GrayConfigTableDef]

  def add(graySystem: GrayServer): Future[String] = {
    db.run(grayServers += graySystem).map(res => "grayServers successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }
  def delete(id: Long): Future[Int] = {
    db.run(grayServers.filter(_.id === id).delete)
  }

  def update(graySystem: GrayServer): Future[Int] = {
    db.run(grayServers.filter(_.id === graySystem.id).update(graySystem))
  }
  def get(id: Long): Future[GrayServer] = {
    db.run(grayServers.filter(_.id === id).result.head)
  }

  def listAll: Future[Seq[GrayServer]] = {
    db.run(grayServers.result)
  }

  def list(id: Int): Future[Seq[GrayServer]] = {
//    db.run(graySystems
//      .join(grayConfigs)
//      .on((t1,t2) => t1.id === t2.systemId )
//      .filter{case (t1, t2) => t1.systemType === id }
//      .map{ case (t1, t2) => t1 }.result )
    db.run(grayServers.filter(_.systemType === id).result)
  }

  def detail(id:Long): Future[Seq[GrayConfig]]= {
       db.run(grayConfigs.filter(_.systemId === id).result)
  }
}