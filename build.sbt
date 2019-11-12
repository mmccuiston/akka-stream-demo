enablePlugins( DockerPlugin, JavaAppPackaging )

name := "akka-streams-demo"

organization := "io.github.mmccuiston"

version := "1.0"

scalaVersion := "2.12.9"

lazy val akkaVersion = "2.6.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % "10.1.10",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
).map( _.withSources() )
