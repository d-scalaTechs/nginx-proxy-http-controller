package controllers
import java.util.UUID
import javax.inject.{Inject, Singleton}

import play.api.{Configuration, Logger}
import play.api.mvc._


/**
 *
 * @author Eric  2016/8/26 20:44
 */
@Singleton
class  LoginController@Inject()(configuration:Configuration)  extends Controller {

  def login = Action { implicit request =>
    val state = UUID.randomUUID().toString()
    val fullUri = new StringBuffer()
    fullUri.append(configuration.getString("OAUTH_URL").get).append("/oauth/authorize")
      .append("?")
      .append("response_type=code&scope=read write")
      .append("&client_id=").append(configuration.getString("OAUTH_CLIENT_ID").get)
      .append("&redirect_uri=").append(configuration.getString("OAUTH_REDIRECT_URL").get)
      .append("&state=").append(state)
       Logger("login redirect url:  "+ fullUri)
       Redirect(fullUri.toString).withSession("oauth-state" -> state)
    }

  def logout = Action { implicit request =>
    Redirect(configuration.getString("OAUTH_URL").get+"/logout.do")
  }
}
