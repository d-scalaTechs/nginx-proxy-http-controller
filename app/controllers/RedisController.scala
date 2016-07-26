package controllers

/**
 *
 * @author Eric on 2016/7/21 15:55
 */
import javax.inject.{Inject, Singleton}

import play.api.mvc.{Action, Controller}
import play.cache.CacheApi
import pojos._
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class RedisController@Inject()(cache: CacheApi) extends Controller {


  def page = Action{ implicit request =>
    Ok(views.html.redisOp())
  }

  def insertData = Action{ implicit request =>
    println("1111111111111111111")
    cache.set( "keydwp", "valuedwp" )
    println("1111111111111111111")

    Ok(views.html.redisOp())
  }


  def getData = Action{ implicit request =>
    cache.get[ String ]( "keydwp" )
    Ok(views.html.redisOp())
  }
}
