package controllers
import java.util.UUID

import play.api._
import util.OAuth2
import play.api.mvc.{Action, Controller}

/**
 *
 * @author Shadow 
 * @date 2016/8/26 20:44
 */
object  LoginController  extends Controller {

  def index = Action { implicit request =>
    val oauth2 = new OAuth2(Play.current)
    val callbackUrl = util.routes.OAuth2.callback(None, None).absoluteURL()
    val scope = "repo"   // github scope - request repo access
    val state = UUID.randomUUID().toString  // random confirmation string
    val redirectUrl = oauth2.getAuthorizationUrl(callbackUrl, scope, state)
      Ok(views.html.index("Your new application is ready.", redirectUrl)).
        withSession("oauth-state" -> state)
    }

}
