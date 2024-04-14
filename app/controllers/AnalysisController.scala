//package controllers
//
//import javax.inject._
//import play.api.mvc._
//import services.KafkaService
//
//import scala.concurrent.{ExecutionContext, Future}
//
//@Singleton
//class AnalysisController @Inject()(cc: ControllerComponents, kafkaService: KafkaService)
//                                  (implicit ec: ExecutionContext) extends AbstractController(cc) {
//
//  def index(): Action[AnyContent] = Action.async { implicit request =>
//    kafkaService.consumeMessages().map(_ => Ok("Messages consumed from Kafka in AnalysisController"))
//  }
//}

package controllers

import javax.inject._
import play.api.mvc._
import services.KafkaService

import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

@Singleton
class AnalysisController @Inject()(cc: ControllerComponents, kafkaService: KafkaService)
                                  (implicit ec: ExecutionContext) extends AbstractController(cc) {

  def analysis(): Action[AnyContent] = Action.async { implicit request =>
    kafkaService.consumeMessages().map { messages =>
      Ok(views.html.analysis(messages)) 
    }.recover {
      case NonFatal(ex) =>
        InternalServerError("An error occurred while consuming messages from Kafka: " + ex.getMessage)
    }
  }
}
