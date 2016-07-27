package pojos

import play.api.data.Form
import play.api.data.Forms._

/**
 *
 * @author Eric on 2016/7/21 15:51
 */
case class GraySystemFormData(name: String, description: String, entrance: String, systemType:Int)
object GraySystemForm {

  val form = Form(
    mapping(
      "name" -> nonEmptyText,
      "description" -> nonEmptyText,
      "entrance" -> nonEmptyText,
      "systemType" -> number
    )(GraySystemFormData.apply)(GraySystemFormData.unapply)
  )
}