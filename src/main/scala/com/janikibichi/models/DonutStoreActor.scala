package com.janikibichi.models

import akka.actor.{Actor, ActorLogging}

object DonutStoreProtocol{
  case class Info(name:String)
}

class DonutStoreActor extends Actor with ActorLogging{
  import DonutStoreProtocol._

   def receive: Receive = {
     case Info(name) if name == "vanilla" =>
       log.info(s"Found valid $name donut")
       sender ! true

     case Info(name) =>
       log.info(s"Found valid $name donut")
       sender ! false

     case event @ _ =>
       log.info(s"Event $event is not allowed.")
       throw new IllegalStateException(s"Event $event is not allowed")
   }
}