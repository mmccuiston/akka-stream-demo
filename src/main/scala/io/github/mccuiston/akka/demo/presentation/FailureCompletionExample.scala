package io.github.mccuiston.akka.demo.presentation

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Sink, Source}

object FailureCompletionExample extends App {
  implicit val system: ActorSystem = ActorSystem( "demo" )
  import system.dispatcher

  val sink = Sink.foreach[Int]( println ) //Prints each element received

  val source = Source
    .fromIterator( () => Iterator( -2, -1, 0, 1, 2 ) )
    .map( number => number / number )

  val terminationWatch = source.watchTermination() { ( _, futureDone ) =>
    for (done <- futureDone) {
      println( s"Stream terminated with $done" )
    }
  }

  terminationWatch.runWith( sink )

  Thread.sleep( 1000 )
  system.terminate()
}
