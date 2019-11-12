package io.github.mccuiston.akka.demo.presentation

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Sink, Source}

object MaterializedValueExample extends App {
  implicit val system: ActorSystem = ActorSystem( "demo" )

  val source = Source.maybe[String]

  val sink = Sink.foreach( println )

  val promise = source.to( sink ).run()

  promise.success( Some( "Akka FTW!" ) )

  system.terminate()

}
