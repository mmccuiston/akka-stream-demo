package io.github.mccuiston.akka.demo.presentation

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Sink, Source}

object SinkCompletionExample extends App {
  implicit val system: ActorSystem = ActorSystem( "demo" )
  import system.dispatcher

  val sink = Sink.head[String] //Completes after receiving single value

  val source = Source.repeat( "Hello World" ) //Repeats forever

  val terminationWatch = source.watchTermination() { ( _, futureDone ) =>
    for (done <- futureDone) {
      println( s"Stream terminated successfully with $done" )
    }
  }

  val futureHead = terminationWatch.runWith( sink )

  futureHead.onComplete( head => println( s"Received $head" ) )

  system.terminate()
}
