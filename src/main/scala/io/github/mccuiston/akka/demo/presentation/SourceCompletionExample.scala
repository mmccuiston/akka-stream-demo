package io.github.mccuiston.akka.demo.presentation

import akka.actor.ActorSystem
import akka.stream.scaladsl.Source

import scala.util.{Failure, Success}

object SourceCompletionExample extends App {
  implicit val system: ActorSystem = ActorSystem( "demo" )
  import system.dispatcher

  Source
    .single( "Hello World" )
    .watchTermination() { ( _, futureDone ) =>
      futureDone.onComplete {
        case Success( value ) =>
          println( "Stream terminated successfully" )
        case Failure( exception ) =>
          println( s"Stream terminated with failure ${exception}" )
      }

    }
    .runForeach( println )

  system.terminate()
}
