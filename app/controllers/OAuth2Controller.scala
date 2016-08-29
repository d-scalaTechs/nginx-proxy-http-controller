package controllers


import javax.inject._

import play.api.mvc._
import play.api.{Configuration}

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.http.{HeaderNames, MimeTypes}
import play.api.libs.ws._

import scala.concurrent.Future
/**
  * Created by Shadow on 2016/8/29.
  */
class OAuth2Controller @Inject()(configuration:Configuration,ws:WSClient ) extends Controller {

  def getToken(code: String): Future[String] = {
    val accessTokenUri = "http://oauth.kuaisuwang.com/oauth/token"
    val authId = configuration.getString("OAUTH_CLIENT_ID").get
    val authSecret = configuration.getString("OAUTH_CLIENT_SECRET").get

    val redirectUri = "http://gray.xxxxx.com/oauth_code_callback"
      val tokenResponse =  ws.url(accessTokenUri).withQueryString("code"-> code)
                      .withQueryString( "grant_type"->"authorization_code")
                      .withQueryString( "client_secret"->authSecret)
                      .withQueryString( "redirect_uri"->redirectUri)
                      .withQueryString( "client_id"->authId)
                      .withHeaders("Accept" -> "application/json").post("")

    val futureResult: Future[String] = tokenResponse.map {
      response =>
        println((response.json \ "access_token").as[String])
        println((response.json \ "token_type").as[String])
        println((response.json \ "refresh_token").as[String])
         println(((response.json \ "expires_in")).as[Int])
        println(((response.json \ "scope").as[String]))
        (response.json \ "access_token").as[String]
    }
    futureResult
  }

  def callback(codeOpt: Option[String] = None, stateOpt: Option[String] = None) = Action.async { implicit request =>
    println(codeOpt)
    println(stateOpt)
    (for {
      code <- codeOpt
      state <- stateOpt
      oauthState <- request.session.get("oauth-state")
    } yield {
      if (state == oauthState) {
          getToken(code).map { accessToken =>
//          Redirect("").withSession("oauth-token" -> accessToken)
            Redirect("/home")
        }.recover {
          case ex: IllegalStateException => Unauthorized(ex.getMessage)
        }
      }
      else {
        Future.successful(BadRequest("Invalid login"))
      }
    }).getOrElse(Future.successful(BadRequest("No parameters supplied")))
//    Future.successful(BadRequest("Invalid github login"))
  }
  def success() = Action.async { request =>
    Future.successful(Redirect("/home"))
  }
}
