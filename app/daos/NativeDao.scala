package daos

import javax.inject.Inject
import models._
import play.api.db.Database

import scala.collection.mutable.ListBuffer

/**
 * Created by eric on 16/7/30.
 */
class NativeDao @Inject()(db: Database) {
  def getGrayServer(id:Long) : GrayServerDto= {
    val conn = db.getConnection()
    try {
      val stmt = conn.createStatement
      val rs = stmt.executeQuery("SELECT t1.id as id,t1.name as name,t1.description as description,t1.entrance as entrance," +
        "t1.server_type as serverType,t2.name as subSystemName,t1.status as status from grey_servers t1 left join sub_systems t2 " +
        "on t1.sub_system_id=t2.id where t1.id="+id)
      rs.next()
      val serverId = rs.getInt("id")
      val name = rs.getString("name")
      val description= rs.getString("description")
      val entrance= rs.getString("entrance")
      val server_type= rs.getInt("serverType")
      val status = if(rs.getInt("status")==0) "禁用" else{"启用"}
      val subSystemName= rs.getString("subSystemName")
      new GrayServerDto(serverId,name,description,entrance,server_type,subSystemName,status)
    } finally {
      conn.close()
    }
  }


  def getSubServerName() : List[SubSystem]= {
    val nameList = ListBuffer[SubSystem]()

    val conn = db.getConnection()
    try {
      val stmt = conn.createStatement
      val rs = stmt.executeQuery("SELECT * from sub_systems")
      while(rs.next()){
        nameList.append(new SubSystem(rs.getInt("id"),rs.getString("name")))
      }
      nameList.toList
    } finally {
      conn.close()
    }
  }


  def getUniqConf(id:Long, key:String, value:String) : Int= {
    var count=0;
    val conn = db.getConnection()
    try {
      val stmt = conn.createStatement
      val rs = stmt.executeQuery("SELECT count(*) as rn from grey_configs where server_id="+id +" and `key`='"+key+"' and value='"+value+"'")
      while(rs.next()){
        count = rs.getInt("rn")
      }
      println("count: " + count)
      count
    } finally {
      conn.close()
    }
  }

  def getEntrancesBySubSystemIdAndValue(subSystemId:Long, value:String) : ListBuffer[String]= {
    val syncDataList = ListBuffer[String]()
    val conn = db.getConnection()
    try {
      val stmt = conn.createStatement
      val rs = stmt.executeQuery("SELECT t1.`key` as field, t2.entrance as entrance " +
        " FROM grey_configs AS t1 LEFT JOIN   grey_servers AS t2 ON t1.server_id = t2.id" +
        " LEFT JOIN  sub_systems AS t3 ON t2.sub_system_id = t3.id " +
        " WHERE   t1.value = '"+value+"' AND t3.id = "+subSystemId)
      while(rs.next()){
        syncDataList.append(rs.getString("field")+" ->  "+rs.getString("entrance")+"\n")
      }
      syncDataList
    } finally {
      conn.close()
    }
  }


  def deleteRelativeConfs(id:Long)={
    val conn = db.getConnection()
    try {
      val stmt = conn.createStatement
      stmt.execute(s"delete from grey_configs where server_id =${id}")
    } finally {
      conn.close()
    }
  }
}
