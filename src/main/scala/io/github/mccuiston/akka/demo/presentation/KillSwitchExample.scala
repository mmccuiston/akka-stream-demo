package io.github.mccuiston.akka.demo.presentation

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Keep, Sink, Source}
import akka.stream.KillSwitches

import scala.concurrent.duration._

object KillSwitchExample extends App {
  implicit val system: ActorSystem = ActorSystem( "demo" )

  val source = Source( Stream.from( 0 ) )
    .throttle( 1, 1.second )

  val sink = Sink.foreach( println )

  val killSwitch = source
    .viaMat( KillSwitches.single[Int] )( Keep.right )
    .to( sink )
    .run()

  Thread.sleep( 5000 )

  killSwitch.shutdown()

  Thread.sleep( 5000 )

  system.terminate()
}
