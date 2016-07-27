package models

import java.util.Date

/**
 *
 * @author Eric on  2016/7/21 15:50
 */
case class User(id: Long, firstName: String, lastName: String, mobile: Long, email: String)
case class GrayServer(id: Long, name: String, description: String, entrance: String,serverType:Int,subSystem:String,status:Int)
case class GrayConfig(id: Long, key: String, value: String, systemId: Long, updatedAt: Date)
