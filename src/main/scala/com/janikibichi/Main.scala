
import com.janikibichi.models.{DonutBakerActorProtocol}
import com.typesafe.scalalogging.LazyLogging

object Main extends App with LazyLogging{
  import com.janikibichi.utils.AppUtil._
  import scala.concurrent.duration._

  println("Step 1: Actor System Created on Import")

  println("Step 2: We define DonutBakerActor with unbecome() and unbecome() states")

  println("Step 3: We create DonutBakerActor")
  val bakingActor = actorSystem.actorOf(DonutBakerActorProtocol.props(),DonutBakerActorProtocol.name)

  println("Step 4: Send events to actor to switch states and process events")
  bakingActor ! "BakeDonut"
  Thread.sleep(2000)

  println("Step 5: Using system.scheduler to send periodic events to actor")
  actorSystem.scheduler.schedule(1.seconds,2.seconds){
    bakingActor ! "BakeVanilla"
    Thread.sleep(1000)
  }

}