package io.github.mccuiston.akka.demo.presentation

import akka.actor.ActorSystem
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{Sink, Source}

object MaterializedValueExample extends App {
  implicit val system: ActorSystem = ActorSystem( "demo" )

  val source = Source.queue[String]( 256, OverflowStrategy.backpressure )

  val sink = Sink.foreach( println )

  val queue = source.to( sink ).run() //Returns Mat of LHS

  queue.offer( "Akka FTW!" )

  system.terminate()

}
