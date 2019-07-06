package com.janikibichi.routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import com.typesafe.scalalogging.LazyLogging
import spray.json.DefaultJsonProtocol
import akka.http.scaladsl.server.Directives._

final case class AkkaHttpServer(app:String, version:String)
final case class Donut(name:String,price:Double)

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol{
  import spray.json._
  implicit val donutPrinter = PrettyPrinter

  implicit val serverFormat = jsonFormat2(AkkaHttpServer.apply)
  implicit val donutFormat = jsonFormat2(Donut.apply)
}

class DonutRoutes extends JsonSupport with LazyLogging{
  def route(): Route ={
    path("create-donut"){
      post{
        entity(as[Donut]){ donut =>
          logger.info(s"Creating donut = $donut")
          complete(StatusCodes.Created,s"Created donut = $donut")
        }
      }
    }
  }
}