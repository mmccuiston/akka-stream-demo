package io.github.mccuiston.akka.demo.presentation

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Sink, Source}

object SourceSinkHelloWorld extends App {
  implicit val system: ActorSystem = ActorSystem( "demo" )

  val source = Source.repeat( "Hello World" ) // Creates Source (Graph[SourceShape, _] that repeats single value
  val sink = Sink.foreach[String]( println ) // Creates Sink (Graph[SinkShape], _] that prints received Strings

  source
    .to( sink ) // Connects Outlet of Source to Inlet of Sink and returns RunnableGraph
    .run() // Run it!

  Thread.sleep( 3000 )
  system.terminate()
}
