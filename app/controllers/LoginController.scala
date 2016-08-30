package controllers
import java.util.UUID
import javax.inject.Singleton

import play.api.Logger
import play.api.mvc._


/**
 *
 * @author Eric  2016/8/26 20:44
 */
@Singleton
class  LoginController  extends Controller {

  def index = Action { implicit request =>
    val state = UUID.randomUUID().toString()
    val fullUri = new StringBuffer()
    fullUri.append("http://oauth.kuaisuwang.com/oauth/authorize")
      .append("?")
      .append("response_type=code&scope=read write")
      .append("&client_id=gray-client")
      .append("&redirect_uri=").append("http://gray.xxxxx.com/oauth_code_callback").append("")
      .append("&state=").append(state)

       Logger.info(s"fullUril: ${fullUri}" )
       Redirect(fullUri.toString).withSession("oauth-state" -> state)
    }

}
