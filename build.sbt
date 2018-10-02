name := """oAuth2"""
organization := "sk.scala"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
  ws,
  "com.pauldijou" %% "jwt-core" % "0.18.0")
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "sk.scala.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "sk.scala.binders._"
