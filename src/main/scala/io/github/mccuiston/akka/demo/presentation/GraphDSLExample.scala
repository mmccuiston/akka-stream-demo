package io.github.mccuiston.akka.demo.presentation

import akka.actor.ActorSystem
import akka.stream.ClosedShape
import akka.stream.scaladsl.{Flow, GraphDSL, Merge, RunnableGraph, Sink, Source}
import GraphDSL.Implicits._

import scala.concurrent.duration._

object GraphDSLExample extends App {
  implicit val system = ActorSystem( "demo" )

  val evens = Source( Stream.from( 0, step = 2 ) )

  val odds = Source( Stream.from( 1, step = 2 ) )

  val printlnSink = Sink.foreach( println )

  val zippedAppendGraph = GraphDSL.create() { implicit builder =>
    val merge = builder.add( Merge[Int]( 2 ) )

    val flow = Flow[Int]
      .map( num => s"number is $num" )
      .throttle( 10, 1.second )

    evens ~> merge
    odds ~> merge ~> flow ~> printlnSink

    ClosedShape
  }

  RunnableGraph
    .fromGraph( zippedAppendGraph )
    .run()

  Thread.sleep( 5000 )
  system.terminate()

}
