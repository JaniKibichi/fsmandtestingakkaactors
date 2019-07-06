package com.janikibichi.models

import akka.actor.ActorSystem
import akka.testkit.{DefaultTimeout, ImplicitSender, TestActorRef, TestKit}
import com.janikibichi.models.DonutStoreProtocol.Info
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.util.Success

class DonutStoreActorTests
extends TestKit(ActorSystem("DonutStoreActorTests"))
with ImplicitSender
with DefaultTimeout
with WordSpecLike
with BeforeAndAfterAll
with Matchers{

  "Sending Tell Pattern Info(vanilla) message to DonutStoreActor" should{
    "reply back with true" in{
      val testActor = TestActorRef[DonutStoreActor]
      testActor ! Info("vanilla")
      expectMsg(true)
    }
  }

  import scala.concurrent.duration._
  "DonutStoreActor" should{
    "respond back within 100 millis" in{
      within(100 millis){
        val testActor = TestActorRef[DonutStoreActor]
        testActor ! Info("vanilla")
        Thread.sleep(500)
        expectMsg(true)
      }
    }
  }

  "Sending Ask Pattern Info(plain) message to DonutStoreActor" should{
    "reply back with false" in{
      import akka.pattern._
      val testActor  = TestActorRef[DonutStoreActor]
      val result = testActor ? Info("plain")
      val Success(reply:Boolean) = result.value.get
      reply shouldBe false
    }
  }

  override protected def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }
}