package models

import java.util.Date


case class GrayServer(id: Long, name: String, description: String, entrance: String,serverType:Int,subSystemId:Long,status:Int)
case class GrayServerDto(id: Long, name: String, description: String, entrance: String,serverType:Int,subSystemName:String,status:String)
case class GrayConfig(id: Long, key: String, value: String, systemId: Long, updatedAt: Date)
case class SubSystem(id: Long, name: String)
//case class SyncData(key: String, entrance: String)
