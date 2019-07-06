package com.janikibichi.utils

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.util.Timeout
import scala.concurrent.duration._

object AppUtil {
  implicit val requestTimeout: Timeout = 15.seconds
  implicit val actorSystem = ActorSystem("akkafsm")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = actorSystem.dispatcher
}