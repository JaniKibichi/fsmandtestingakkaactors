package com.janikibichi.models

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.janikibichi.routes.DonutRoutes
import org.scalatest.{Matchers, WordSpec}

class DonutQueryParametersTest
extends WordSpec
with Matchers
with ScalatestRouteTest{
  val donutRoutes = new DonutRoutes().route()

  "Query Parameters Tests" should{
    "match the output for the URL /donut/prices?donutName" in{
      Get("/donut/prices?donutName=plain%20donut") ~> donutRoutes ~>check {
        responseAs[String] shouldEqual "Received parameter: donutName=plain donut"
        status shouldEqual StatusCodes.OK
      }
    }
  }

  "Check for required donutName query parameter at /donut/prices" in{
    Get("/donut/prices?") ~> Route.seal(donutRoutes) ~> check{
      responseAs[String] shouldEqual "Request is missing required query parameter 'donutName'"
      status shouldEqual StatusCodes.NotFound
    }
  }

  "Validate the pass-through of required and optional parameters in /donut/bake" in{
    Get("/donut/bake?donutName=plain%20donut") ~> donutRoutes ~> check{
      responseAs[String] shouldEqual "Received Parameters: donutName=plain donut and topping=sprinkles"
      status shouldEqual StatusCodes.OK
    }
  }

  "Verify Typed Parameters for /ingredients" in{
    Get("/ingredients?donutName=plain%20donut&priceLevel=1.50") ~> donutRoutes ~> check{
      responseAs[String] shouldEqual "Received parameters: donutName=plain donut, priceLevel=1.5"
      status shouldEqual StatusCodes.OK
    }
  }

  "Check for wrong values being passed through to the priceLevel query param at /ingredients" in{
    Get("/ingredients?donutName=plain%20donut&priceLevel=cheap") ~> Route.seal(donutRoutes) ~> check{
      responseAs[String] shouldEqual """The query parameter 'priceLevel' was malformed: 'cheap' is not a valid 64-bit floating point value""".stripMargin
      status shouldEqual StatusCodes.BadRequest
    }
  }

  "Verify CSV parameters for /bake-donuts" in{
    Get("/bake-donuts?ingredients=flour,sugar,vanilla") ~> donutRoutes ~> check{
      responseAs[String] shouldEqual "Received CSV parameter: ingredients=List(flour,sugar,vanilla)"
      status shouldEqual StatusCodes.OK 
    }
  }
}