package com.janikibichi.models

import akka.actor.{Actor, ActorLogging, Props}

object DonutBakerActorProtocol{
  def props() = Props(classOf[DonutBakerActor])
  def name = "donut-baker-actor"
}

class DonutBakerActor extends Actor with ActorLogging{
  import context._

  def receive ={
    case "BakeDonut"=>
      log.info("becoming bake state")
      become{
        case "BakeVanilla"=>
          log.info("baking vanilla")

        case "BakePlain" =>
          log.info("baking plain")

        case "StopBaking" =>
          log.info("stopping to bake")

        case event @ _ =>
          log.info(s"Allowed events [BakeVanilla,BakePlain,StopBaking], event = $event")
      }
    case event @ _ =>
      log.info(s"Allowed events [BakeDonut], event = $event")
  }
}