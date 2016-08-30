package util

import play.api.Application
import play.api.Play
import play.api.http.{MimeTypes, HeaderNames}
import play.api.libs.ws.WS
import play.api.mvc.{Results, Action, Controller}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class OAuth2(application: Application) {
  lazy val authId = application.configuration.getString("OAUTH_CLIENT_ID").get
  lazy val authSecret = application.configuration.getString("OAUTH_CLIENT_SECRET").get

  def getAuthorizationUrl(redirectUri: String, scope: String, state: String): String = {
    val baseUrl = application.configuration.getString("OAUTH.REDIRECT.URL").get
    baseUrl.format(authId, redirectUri, scope, state)
  }

  def getToken(code: String): Future[String] = {
    val tokenResponse = WS.url("https://github.com/login/oauth/access_token")(application).
      withQueryString("client_id" -> authId,
        "client_secret" -> authSecret,
        "code" -> code).
      withHeaders(HeaderNames.ACCEPT -> MimeTypes.JSON).
      post(Results.EmptyContent())

    tokenResponse.flatMap { response =>
      (response.json \ "access_token").asOpt[String].fold(Future.failed[String](new IllegalStateException("Sod off!"))) { accessToken =>
        Future.successful(accessToken)
      }
    }
  }
}

object OAuth2{

}