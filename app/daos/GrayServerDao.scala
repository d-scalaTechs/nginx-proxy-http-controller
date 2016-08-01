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
  def serverType= column[Int]("server_type")
  def subSystemId= column[Long]("sub_system_id")
  def status= column[Int]("status")

  override def * =
    (id, name, description, entrance,serverType,subSystemId,status) <>(GrayServer.tupled, GrayServer.unapply)
}


class GrayServers @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {

  val grayServers = TableQuery[GrayServersTableDef]
  val grayConfigs = TableQuery[GrayConfigTableDef]
  val subSystems = TableQuery[SubSystemsTableDef]

  def add(graySystem: GrayServer): Future[String] = {
    db.run(grayServers += graySystem).map(res => "grayServers successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def delete(id: Long): Future[Int] = {
    db.run(grayServers.filter(_.id === id).delete)
  }

  def updateGrayServerStatus(id: Long,status:Int): Future[Int] = {
    db.run(grayServers.filter(_.id === id).map(_.status).update(status))
  }

  def update(graySystem: GrayServer): Future[Int] = {
    db.run(grayServers.filter(_.id === graySystem.id).update(graySystem))
  }

  def get(id: Long): Future[Option[GrayServer]] = {
    db.run(grayServers.filter(_.id === id).result.headOption)
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
    db.run(grayServers.filter(_.serverType === id).result)
  }

  def detail(id:Long): Future[Seq[GrayConfig]]= {
       db.run(grayConfigs.filter(_.serverId === id).result)
  }


  def buildRedisKeyAndValue: Future[Seq[(String,String,String,String)]]={
    db.run(
      grayServers.filter(_.status===1).
        join(grayConfigs).on{(t1,t2) => t1.id === t2.serverId }.
        join(subSystems).on{case ((t1,t2),t3)=>t1.subSystemId === t3.id}
        .map{case ((t1,t2),t3)=>(t3.name,t2.key,t2.value,t1.entrance)}.result
    )
  }

  def buildRedisKey: Future[Seq[(Int,String)]]={
    db.run(
      grayServers.filter(_.status===1).join(subSystems).on((t1,t2)=>t1.subSystemId === t2.id).map{case(t1,t2)=>(t1.serverType,t2.name)}.result
    )
  }
}