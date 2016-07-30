package daos

import javax.inject.Inject
import models._
import play.api.db.Database

/**
 * Created by eric on 16/7/30.
 */
class NativeDao @Inject()(db: Database) {
  def getGrayServer(id:Long) : GrayServerDto= {
    val conn = db.getConnection()
    try {
      val stmt = conn.createStatement
      val rs = stmt.executeQuery("SELECT t1.id as id,t1.name as name,t1.description as description,t1.entrance as entrance," +
        "t1.server_type as serverType,t2.name as subSystemName,t1.status as status from grey_servers t1 left join sub_systems t2 on t1.id=t2.id where t1.id="+id)
      rs.next()
      val serverId = rs.getInt("id")
      val name = rs.getString("name")
      val description= rs.getString("description")
      val entrance= rs.getString("entrance")
      val server_type= rs.getInt("serverType")
      val status = if(rs.getInt("id")==1) "启动" else{"禁用"}
      val subSystemName= rs.getString("subSystemName")
      new GrayServerDto(serverId,name,description,entrance,server_type,subSystemName,status)
    } finally {
      conn.close()
    }
  }
}
