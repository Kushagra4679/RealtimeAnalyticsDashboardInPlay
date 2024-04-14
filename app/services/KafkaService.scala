//package services
//
//import akka.Done
//import akka.actor.ActorSystem
//import javax.inject.Inject
//import akka.kafka.scaladsl.Consumer
//import akka.kafka.{ConsumerSettings, Subscriptions}
//import akka.stream.scaladsl.{Sink, Source}
//import akka.stream.{Materializer, SystemMaterializer}
//import org.apache.kafka.clients.consumer.ConsumerConfig
//import org.apache.kafka.common.serialization.StringDeserializer
//
//import javax.inject.Inject
//import scala.concurrent.{ExecutionContext, Future}
//
//class KafkaService @Inject()(actorSystem: ActorSystem)(implicit ec: ExecutionContext) {
//  private implicit val materializer: Materializer = SystemMaterializer(actorSystem).materializer
//
//  private val bootstrapServers = "localhost:9092"
//  private val topic = "data-generation"
//  private val groupId = "1234"
//
//  private val consumerSettings: ConsumerSettings[String, String] =
//    ConsumerSettings(actorSystem, new StringDeserializer, new StringDeserializer)
//      .withBootstrapServers(bootstrapServers)
//      .withGroupId(groupId)
//      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
//
//  def consumeMessages(): Future[Done] = {
//    Consumer
//      .plainSource(consumerSettings, Subscriptions.topics(topic))
//      .map { record =>
//        // Process the message here
//        println(s"Received message in AnalysisController: ${record.value()}")
//      }
//      .runWith(Sink.ignore)
//  }
//}

package services

import akka.Done
import akka.actor.ActorSystem
import javax.inject.Inject
import akka.kafka.scaladsl.Consumer
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.stream.scaladsl.{Sink, Source}
import akka.stream.{Materializer, SystemMaterializer}
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class KafkaService @Inject()(actorSystem: ActorSystem)(implicit ec: ExecutionContext) {
  private implicit val materializer: Materializer = SystemMaterializer(actorSystem).materializer

  private val bootstrapServers = "localhost:9092"
  private val topic = "data-generation"
  private val groupId = "1234"

  private val consumerSettings: ConsumerSettings[String, String] =
    ConsumerSettings(actorSystem, new StringDeserializer, new StringDeserializer)
      .withBootstrapServers(bootstrapServers)
      .withGroupId(groupId)
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
  def consumeMessages(): Future[Seq[String]] =
    Consumer
      .plainSource(consumerSettings, Subscriptions.topics(topic))
      .map(_.value())
      .take(10)
      .runWith(Sink.seq)
}
