package models

import java.util.Date

/**
 *
 * @author Eric on  2016/7/21 15:50
 */
case class User(id: Long, firstName: String, lastName: String, mobile: Long, email: String)
case class GraySystem(id: Long, name: String, description: String, entrance: String,systemType:Int)
case class GrayConfig(id: Long, key: String, value: String, targetId: Long, updatedAt: Date)
