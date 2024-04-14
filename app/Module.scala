import akka.actor.ActorSystem
import com.google.inject.AbstractModule

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ActorSystem]).toInstance(ActorSystem("MyActorSystem"))
  }
}