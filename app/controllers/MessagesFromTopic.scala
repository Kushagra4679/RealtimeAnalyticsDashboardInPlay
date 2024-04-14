package controllers

import java.util.Properties
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import scala.jdk.CollectionConverters._

object KafkaConsumer {
  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group")
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")

    val consumer = new KafkaConsumer[String, String](props)
    consumer.subscribe(java.util.Collections.singletonList("Google"))

    try {
      while (true) {
        val records = consumer.poll(java.time.Duration.ofMillis(100))
        for (record <- records.asScala) {
          println("Received message: " + record.value())
          // Process the message (extract document title, etc.)
        }
      }
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally {
      consumer.close()
    }
  }
}
