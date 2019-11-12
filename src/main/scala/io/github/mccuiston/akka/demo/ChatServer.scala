package io.github.mccuiston.akka.demo

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.ws.Message
import akka.stream.scaladsl.{BroadcastHub, Flow, Keep, MergeHub}

object ChatServer extends App {

  implicit val system: ActorSystem = ActorSystem( "demo" )

  val ( sink, source ) =
    MergeHub
      .source[Message]( perProducerBufferSize = 16 )
      .toMat( BroadcastHub.sink( bufferSize = 256 ) )( Keep.both )
      .run() //Runs stream and exposes materialized values Source and Sink

  val webSocketDirective = path( "chat" ) {
    handleWebSocketMessages(
      Flow.fromSinkAndSource( sink, source )
    )
  }

  val indexDirective = path( "index.html" ) {
    getFromResource( "index.html" )
  }

  Http().bindAndHandle(
    webSocketDirective ~ indexDirective,
    "0.0.0.0",
    8080
  )

}
