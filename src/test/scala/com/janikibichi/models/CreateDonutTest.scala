package com.janikibichi.models

import akka.http.scaladsl.model.{HttpEntity, HttpMethods, HttpRequest, MediaTypes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import com.janikibichi.routes.DonutRoutes
import org.scalatest.{Matchers, WordSpec}

class CreateDonutTest
extends WordSpec
with Matchers
with ScalatestRouteTest{

  val donutRoutes = new DonutRoutes().route()

  "Donut API" should{
    "Create a valid Donut when posting JSON to /create-donut path" in{
      val jsonDonutInput = ByteString("""{"name":"plain donut","price":1.50}""")

      val httpPostCreateDonut = HttpRequest(
        uri = "http://localhost:8080/create-donut",
        method = HttpMethods.POST,
        entity = HttpEntity(MediaTypes.`application/json`,jsonDonutInput)
      )
      httpPostCreateDonut ~> donutRoutes ~> check{
        status.isSuccess() shouldEqual true
        status.intValue() shouldEqual 201
        status.reason shouldEqual "Created"
      }
    }
  }
}