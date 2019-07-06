package com.janikibichi.models

import akka.testkit.{DefaultTimeout, ImplicitSender, TestFSMRef, TestKit}
import com.janikibichi.models.DonutActorFSMProtocol._
import com.janikibichi.utils.AppUtil._
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import scala.concurrent.duration._

class DonutActorFSMTest
extends TestKit(actorSystem)
with ImplicitSender
with DefaultTimeout
with WordSpecLike
with BeforeAndAfterAll
with Matchers{

  private var donutActorFSM: TestFSMRef[BakingStates,BakingData,DonutActorFSM] = _

  override protected def beforeAll(): Unit = {
    donutActorFSM = TestFSMRef(new DonutActorFSM())
  }

  "DonutActorFSM" should{
    "have initial state of BakingStates.Stop" in{
      donutActorFSM.stateName shouldEqual Stop
    }
  }

  "DonutActorFSM" should{
    "process BakeDonut event and switch to the BakingStates.Start state" in{
      donutActorFSM ! BakeDonut

      awaitCond(donutActorFSM.stateName == Start, 2 second, 1 second)
    }
  }

  "DonutActorFSM" should{
    "process StopBaking event and switch to the BakingStates.Stop state" in{
      donutActorFSM ! StopBaking

      awaitCond(donutActorFSM.stateName == Stop, 2 second, 1 second)
    }
  }

  "DonutActorFSM current donut quantity" should{
    "equal to 1 after StopBaking event" in{
      donutActorFSM.stateData.qty shouldEqual 1
    }
  }

  override protected def afterAll(): Unit = {
    TestKit.shutdownActorSystem(actorSystem)
  }
}