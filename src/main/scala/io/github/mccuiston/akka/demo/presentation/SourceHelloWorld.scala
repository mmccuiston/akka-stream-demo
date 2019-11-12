package io.github.mccuiston.akka.demo.presentation

import akka.actor.ActorSystem
import akka.stream.scaladsl.Source

object SourceHelloWorld extends App {
  implicit val system = ActorSystem( "demo" )

  Source
    .repeat( "Hello World" ) // Creates Source (Graph[SourceShape, _] that repeats single value
    .runForeach( println ) // Convenience method for connecting only Outlet and running

  Thread.sleep( 3000 )
  system.terminate()
}
