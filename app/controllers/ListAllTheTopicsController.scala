//package controllers
//
//import javax.inject._
//import scala.concurrent.{Future, Promise}
//import scala.jdk.FutureConverters._
//import scala.jdk.CollectionConverters._
//import org.apache.kafka.clients.admin.AdminClient
//import org.apache.kafka.clients.admin.ListTopicsOptions
//import org.apache.kafka.clients.admin.ListTopicsResult
//import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
//import scala.concurrent.ExecutionContext
//
//@Singleton
//class ListAllTheTopicsController @Inject()(cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {
//
//  def listTopics(): Action[AnyContent] = Action.async { implicit request =>
//    val adminClient = AdminClient.create(Map[String, Object]("bootstrap.servers" -> "localhost:9092").asJava)
//    val options = new ListTopicsOptions().listInternal(true)
//    val result: ListTopicsResult = adminClient.listTopics(options)
//
//    result.names().asScala.map(_.toList).map { topics =>
//      adminClient.close()
//      Ok(views.html.kafka_topics(topics))
//    }.head
//  }
//}

//
//
//import javax.inject._
//import scala.concurrent.{Future, Promise}
//import org.apache.pekko.util.ccompat.JavaConverters.CollectionHasAsScala
//import scala.jdk.FutureConverters._
//import scala.jdk.CollectionConverters._
//import org.apache.kafka.clients.admin.AdminClient
//import org.apache.kafka.clients.admin.ListTopicsOptions
//import org.apache.kafka.clients.admin.ListTopicsResult
//import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
//import scala.concurrent.ExecutionContext
//
//@Singleton
//class ListAllTheTopicsController @Inject()(cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {
//
//  def listTopics(): Action[AnyContent] = Action.async { implicit request =>
//    val adminClient = AdminClient.create(Map[String, Object]("bootstrap.servers" -> "localhost:9092").asJava)
//    val options = new ListTopicsOptions().listInternal(true)
//    val result: ListTopicsResult = adminClient.listTopics(options)
//
//    val promise = Promise[java.util.Set[String]]()
//    result.names().whenComplete { (names, throwable) =>
//      if (throwable != null) {
//        promise.failure(throwable)
//      } else {
//        promise.success(names)
//      }
//    }
//
//    val scalaFuture: Future[java.util.Set[String]] = promise.future
//
//    scalaFuture.map { topics =>
//      adminClient.close()
//      Ok(views.html.kafka_topics(topics.asScala.toList))
//    }
//  }
//}
