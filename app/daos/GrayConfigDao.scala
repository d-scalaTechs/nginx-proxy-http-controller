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

class GrayConfigTableDef(tag: Tag) extends Table[models.GrayConfig](tag, "grey_configs") {
  implicit val JavaUtilDateMapper =
    MappedColumnType.base[java.util.Date, java.sql.Timestamp] (
      d => new java.sql.Timestamp(d.getTime),
      d => new java.util.Date(d.getTime))

  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  def key = column[String]("key")
  def value = column[String]("value")
  def serverId = column[Long]("server_id")
  def updatedAt= column[Date]("updated_at")
  override def * =
    (id, key, value,serverId,updatedAt) <>(GrayConfig.tupled, GrayConfig.unapply)
}


class GrayConfigs @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {

  val grayConfigs = TableQuery[GrayConfigTableDef]

  def add(grayConfig: GrayConfig): Future[String] = {
    db.run(grayConfigs += grayConfig).map(res => "greySystem successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }
  def delete(id: Long): Future[Int] = {
    println("deleteing id")
    db.run(grayConfigs.filter(_.id === id).delete)
  }

  def update(grayConfig: GrayConfig): Future[Int] = {
    db.run(grayConfigs.filter(_.id === grayConfig.id).update(grayConfig))
  }
  def get(id: Long): Future[Option[GrayConfig]] = {
    db.run(grayConfigs.filter(_.id === id).result.headOption)
  }

  def listAll: Future[Seq[GrayConfig]] = {
    db.run(grayConfigs.result)
  }

//  def getRedisKeys: Future[Seq[(Long,String)]] = {
//    db.run(grayConfigs.groupBy{t=>t.serverId->t.key}.map{ case (t1, t2) => t1._1 -> t1._2}.result)
//  }
//
//  def getValuesByServerIdAndKey(serverId: Long,key:String): Future[Seq[String]] ={
//    db.run(grayConfigs.filter(_.serverId === serverId).filter(t=>(t.serverId===serverId) &&(t.key===key)).map{case r=>r.value.trim}.result)
//  }

  def getValuesByServerIdAndValue(serverId: Long,key:String): Future[Seq[String]] ={
    db.run(grayConfigs.filter(_.serverId === serverId).filter(t=>(t.serverId===serverId) &&(t.key===key)).map{case r=>r.value.trim}.result)
  }
}
