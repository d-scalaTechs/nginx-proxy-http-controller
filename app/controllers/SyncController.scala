package controllers

import javax.inject._

import play.api.libs.json.Json
import play.api.mvc._
import redis.clients.jedis.Jedis
import services.GrayServerService

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 *
 * @author Eric on 2016/7/21 15:55
 */
@Singleton
class SyncController @Inject()(grayServerService: GrayServerService) extends Controller {

  def page = Action.async { implicit request =>
      Future.successful( Ok(views.html.sync.render("page")))
  }

  def sync()= Action.async { implicit request =>
    grayServerService.buildRedisKeyAndValue map(keys=>{
//      val jedis = new Jedis("10.168.13.96", 6379);
    val jedis = new Jedis("127.0.0.1", 6379)

      val keysSaved = jedis.keys("gray.*")
      val it= keysSaved.iterator()
      while (it.hasNext){
        val keyToDelete = it.next()
        jedis.del(keyToDelete)
      }
      for (key<-keys){
        val redisKey  = "gray."+key._1+"."+key._2+"."+key._3
        val redisValue = key._4
        println("redis key: " + redisKey +"  -->  redis value: " + redisValue)
        jedis.set(redisKey,redisValue)
      }
      Ok("{\"result\":0}")
    })
//    grayConfigService.getRedisKeys map{keys =>
//      val jedis = new Jedis("127.0.0.1", 6379);
//      for (key<-keys){
//          val redisKey  = "gray."+key._1+"."+key._2
//          grayConfigService.getValuesByServerIdAndKey(key._1,key._2) map{ values=>
//            println("redis Key: "+redisKey+"-->  redis value: " + values.mkString(","))
//            jedis.set(redisKey,new String(values.mkString(",")))
//          }
//       }
//      jedis.close();
//      Ok("{\"result\":0}")
//    }
  }

  def verifyRedis(value:String)= Action.async {
    val redisList = ListBuffer[String]()
    grayServerService.buildRedisKey map{redisKeys =>
//      val jedis = new Jedis("10.168.13.96", 6379)
      val jedis = new Jedis("127.0.0.1", 6379);
      for (key<-redisKeys){
        val redisKey  = "gray."+(if(key._1==1) "web" else if(key._1==2){"oss"})+"."+key._2 +"."+value
        if(jedis.exists(redisKey )){
          redisList.append(jedis.get(redisKey))
        }
      }
      jedis.close()

      Ok("{\"result\":"+(if(redisList.size>0){Json.toJson(redisList)}else{-1})+"}")
    }
  }

    def verifyMysql(value:String)= Action.async {
      val mysqlList = ListBuffer[String]()
      grayServerService.listServersByValue(value) map{servers =>
        println(servers)
        for(server<-servers){
          mysqlList.append(server)
        }
        Ok("{\"result\":"+(if(mysqlList.size>0){Json.toJson(mysqlList)}else{-1})+"}")
      }


  }
}
