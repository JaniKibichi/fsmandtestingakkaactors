package com.janikibichi.models

import akka.actor.{Actor, Props}
import com.janikibichi.models.DonutBakingProtocol.{BakeDonut, BakePlain, BakeVanilla, StopBaking}

object DonutBakingProtocol{
  def props() = Props(classOf[DonutBakingActor])
  def name = "donut-baking-actor"

  sealed trait DonutProtocol
  case object BakeDonut extends DonutProtocol
  case object BakeVanilla extends DonutProtocol
  case object BakePlain extends DonutProtocol
  case object StopBaking extends DonutProtocol
}

class DonutBakingActor extends Actor{
  import context._

  def startBaking: Receive = {
    case BakeDonut =>
      println("becoming bake state")
      become(bake)

        case event @ _ =>
          println(s"Allowed events $BakeDonut, event = $event")
  }
  def bake: Receive ={
    case BakeVanilla =>
      println("baking vanilla")

    case BakePlain =>
      println("baking plain")

    case StopBaking =>
      println("stopping to bake")
      unbecome()

    case event @ _ =>
      println(s"Allowed events $BakeVanilla,$BakePlain,$StopBaking, event = $event")
  }

  def receive: Receive = startBaking
}