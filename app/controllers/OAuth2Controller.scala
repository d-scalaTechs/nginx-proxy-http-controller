package controllers


import javax.inject._

import play.api.mvc._
import play.api.{Configuration, Logger}

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.ws._

import scala.concurrent.Future
/**
  * Created by Shadow on 2016/8/29.
  */
class OAuth2Controller @Inject()(configuration:Configuration,ws:WSClient ) extends Controller {

  def getToken(code: String): Future[String] = {
    val accessTokenUri = configuration.getString("OAUTH_URL").get+"/oauth/token"
    val authId = configuration.getString("OAUTH_CLIENT_ID").get
    val authSecret = configuration.getString("OAUTH_CLIENT_SECRET").get
    val redirectUri = configuration.getString("OAUTH_REDIRECT_URL").get

      val tokenResponse =  ws.url(accessTokenUri).withQueryString("code"-> code)
                      .withQueryString( "grant_type"->"authorization_code")
                      .withQueryString( "client_secret"->authSecret)
                      .withQueryString( "redirect_uri"->redirectUri)
                      .withQueryString( "client_id"->authId)
                      .withHeaders("Accept" -> "application/json").post("")

    val futureResult: Future[String] = tokenResponse.map {
      response =>
        Logger.info((response.json \ "access_token").as[String])
        Logger.info((response.json \ "token_type").as[String])
        Logger.info((response.json \ "refresh_token").as[String])
        Logger.info(((response.json \ "expires_in")).as[Int] + "")
        Logger.info(((response.json \ "scope").as[String]))
        (response.json \ "access_token").as[String]
    }
    futureResult
  }

  def callback(codeOpt: Option[String] = None, stateOpt: Option[String] = None) = Action.async { implicit request =>
    (for {
      code <- codeOpt
      state <- stateOpt
      oauthState <- request.session.get("oauth-state")
    } yield {
      if (state == oauthState) {
          getToken(code).map { accessToken =>
            Redirect("/home")
        }.recover {
          case ex: IllegalStateException => Unauthorized(ex.getMessage)
        }
      }
      else {
        Future.successful(BadRequest("Invalid login"))
      }
    }).getOrElse(Future.successful(BadRequest("No parameters supplied")))
  }
  def success() = Action.async { request =>
    Future.successful(Redirect("/home"))
  }
}
