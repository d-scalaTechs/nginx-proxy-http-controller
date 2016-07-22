package pojos

import play.api.data.Form
import play.api.data.Forms._

/**
 *
 * @author Eric on 2016/7/21 15:51
 */
case class GrayConfigFormData(system: Int, key: String, value: String,targetId:Int)
object GrayConfigForm {

  val form = Form(
    mapping(
      "system" -> number,
      "key" -> nonEmptyText,
      "value" -> nonEmptyText,
      "targetId" -> number
    )(GrayConfigFormData.apply)(GrayConfigFormData.unapply)
  )
}