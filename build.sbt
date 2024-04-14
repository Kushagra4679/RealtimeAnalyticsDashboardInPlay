name := """DashboardUsingPlay"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "3.3.0"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.0" % Test
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.1.0"
libraryDependencies += "org.jsoup" % "jsoup" % "1.14.3"
libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.32"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.4.14"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.8.5"
libraryDependencies += "com.typesafe.akka" %% "akka-stream-kafka" % "4.0.2"
libraryDependencies += "org.elasticsearch.client" % "elasticsearch-rest-high-level-client" % "7.17.16"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
