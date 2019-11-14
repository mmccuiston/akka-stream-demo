package io.github.mccuiston.akka.demo.presentation

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Sink, Source}

object FlowExample extends App {
  implicit val system = ActorSystem( "demo" )

  val intToStringFlow = Flow[Int].map( _.toString )

  val evensSource = Source( 0 to 10 by 2 )
  val oddsSource = Source( 1 to 11 by 2 )

  val sink = Sink.foreach[String]( println )

  evensSource
    .via( intToStringFlow )
    .to( sink )
    .run()

  Thread.sleep( 1000 )

  oddsSource
    .via( intToStringFlow )
    .to( sink )
    .run()

  system.terminate()
}
