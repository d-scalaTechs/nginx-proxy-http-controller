package controllers

import javax.inject._

import play.api.mvc._
import redis.clients.jedis.Jedis
import services.{GrayConfigService, GrayServerService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 *
 * @author Eric on 2016/7/21 15:55
 */
@Singleton
class SyncController @Inject()(grayServerService: GrayServerService,grayConfigService: GrayConfigService) extends Controller {

  def page = Action.async { implicit request =>
      Future.successful( Ok(views.html.sync.render("page")))
  }

  def sync()= Action.async { implicit request =>
    grayConfigService.getRedisKeys map{keys =>
      val jedis = new Jedis("127.0.0.1", 6379);
      for (key<-keys){
          val redisKey  = "gray."+key._1+"."+key._2
          grayConfigService.getValuesByServerIdAndKey(key._1,key._2) map{ values=>
            println("redis Key: "+redisKey+"-->  redis value: " + values.mkString(","))
            jedis.set(redisKey,new String(values.mkString(",")))
          }
       }
      jedis.close();
      Ok("{\"result\":0}")
    }
  }

  def verify(key:String,value:String)= Action.async {
    grayServerService.listServers map{listServers =>
      for(listServer<-listServers){
        println(listServer)
      }
      Ok("{\"result\":0}")
    }
  }
}
