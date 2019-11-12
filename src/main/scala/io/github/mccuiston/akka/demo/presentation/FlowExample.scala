package io.github.mccuiston.akka.demo.presentation

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Sink, Source}

import scala.concurrent.duration._

object FlowExample extends App {
  implicit val system = ActorSystem( "demo" )

  val rollingSumFlow = Flow[Int]
    .throttle( 1, 1.second )
    .scan( 0 )( ( sum, num ) => sum + num )

  val source = Source( 0 to 10 )

  val sink = Sink.foreach[Int]( println )

  val futureValue = source
    .via( rollingSumFlow )
    .to( sink )
    .run()

  Thread.sleep( 10000 )

  system.terminate()
}
