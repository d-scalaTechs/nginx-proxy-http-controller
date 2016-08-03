package controllers

import javax.inject._

import daos.NativeDao
import play.api.libs.json.Json
import play.api.mvc._
import redis.clients.jedis.Jedis
import services.{GrayServerService, SubSystemService}

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global

/**
 *
 * @author Eric on 2016/7/21 15:55
 */
@Singleton
class SyncController @Inject()(nativeDao:NativeDao,grayServerService: GrayServerService,
                               subSystemsService:SubSystemService) extends Controller {

  def page = Action.async { implicit request =>
    subSystemsService.listAll map { subSystems =>
      Ok(views.html.sync.render(subSystems))
    }
  }

  def sync()= Action.async { implicit request =>
    grayServerService.buildRedisKeyAndValue map(keys=>{
      //val jedis = new Jedis("10.168.13.96", 6379);
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
  }

  def verifyRedis(subSystemId:Long,value:String)= Action.async { implicit request =>
      subSystemsService.getSubSystemName(subSystemId)  map { subSystemName =>
        val redisList = ListBuffer[String]()
        //val jedis = new Jedis("10.168.13.96", 6379);
        val jedis = new Jedis("127.0.0.1", 6379);
        val keyPattern="gray." + subSystemName.get.name + ".*." + value
        println("keyPattern: "+keyPattern)
        val keysSaved = jedis.keys(keyPattern)
        val it = keysSaved.iterator()
        while (it.hasNext) {
          val key = it.next()
          println("key: " + key)
          redisList.append(key+" -> "+jedis.get(key)+"\n")
        }
        jedis.close()
        Ok("{\"result\":" + (if (redisList.size > 0) {Json.toJson(redisList)} else {-1}) + "}")
      }
  }

    def verifyMysql(subSystemId:Long, value:String)= Action{
      val mysqlList = nativeDao.getEntrancesBySubSystemIdAndValue(subSystemId,value)
      Ok("{\"result\":"+(if(mysqlList.size>0){Json.toJson(mysqlList)}else{-1})+"}")
  }
}
