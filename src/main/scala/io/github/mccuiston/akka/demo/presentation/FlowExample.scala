package io.github.mccuiston.akka.demo.presentation

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Sink, Source}

object FlowExample extends App {
  implicit val system = ActorSystem( "demo" )

  val digitCountFlow = Flow[Int]
    .map( _.toString )
    .map( _.length )

  val numberSource = Source( 0 to 100 )

  val sink = Sink.foreach[Int]( println )

  numberSource
    .via( digitCountFlow )
    .to( sink )
    .run()

  Thread.sleep( 1000 )

  system.terminate()
}
