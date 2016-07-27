package controllers

/**
 *
 * @author Eric on 2016/7/21 15:55
 */
import javax.inject.{Inject, Singleton}

import play.api.mvc.{Action, Controller}
import redis.clients.jedis.Jedis
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class RedisController @Inject() extends Controller {

  def page = Action{ implicit request =>
    Ok(views.html.redisOp.render("page"))
  }

  def insertData = Action{ implicit request =>
    val jedis = new Jedis("127.0.0.1", 6379);

    //    jedis.set("grey.oss.machine1.staffname",List(111,111))
    jedis.set("grey.oss.machine1.staffname","123,123,123,1212")
    Ok(views.html.redisOp.render("Data"))
  }


  def getData = Action{ implicit request =>
    val jedis = new Jedis("127.0.0.1", 6379);
    val value = jedis.get("grey.oss.machine1.staffname")
    Ok(views.html.redisOp.render(value))
  }
}
