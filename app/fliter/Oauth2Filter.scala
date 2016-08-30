package fliter

import java.util.UUID
import javax.inject.Inject

import akka.stream.Materializer
import play.api.mvc.{Filter, RequestHeader, Result, Results}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future
import play.api.Logger

class Oauth2Filter  @Inject() (implicit val mat: Materializer)   extends Filter with Results{
  def apply(nextFilter: RequestHeader => Future[Result])
           (requestHeader: RequestHeader): Future[Result] = {

    nextFilter(requestHeader).map { result =>
        if(!(requestHeader.cookies.get("PLAY_SESSION").isDefined && requestHeader.cookies.get("PLAY_SESSION").get.value.contains("oauth-state"))){
          val state = UUID.randomUUID().toString()
          val fullUri = new StringBuffer()
          fullUri.append("http://oauth.kuaisuwang.com/oauth/authorize")
            .append("?")
            .append("response_type=code&scope=read write")
            .append("&client_id=gray-client")
            .append("&redirect_uri=").append("http://gray.xxxxx.com/oauth_code_callback").append("")
            .append("&state=").append(state)
              Logger("fullUril:  "+ fullUri)
             Redirect(fullUri.toString).withSession("oauth-state" -> state)
        }else{
          result
        }
    }
  }
}
