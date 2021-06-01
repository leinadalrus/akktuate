name := """akktuate"""
organization := "pty.nfvd.app"

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.12"

lazy val akkaHttpVersion = "10.2.4"
lazy val akkaVersion    = "2.6.14"

idPackagePrefix := Some("pty.nfvd.app")

libraryDependencies += guice

lazy val root = crossProject(JSPlatform, JVMPlatform).in(file("."))
  .settings(
    inThisBuild(List(
      organization    := "pty.nfvd.app",
      scalaVersion    := "2.12.12"
    )),
    name := "Tiddlet",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"                % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json"     % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-actor-typed"         % akkaVersion,
      "com.typesafe.akka" %% "akka-stream"              % akkaVersion,
      "ch.qos.logback"    % "logback-classic"           % "1.2.3",
      "com.lihaoyi" %%% "scalatags" % "0.8.5",

      "com.typesafe.akka" %% "akka-http-testkit"        % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion     % Test,
      "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
      "org.scalatest"     %% "scalatest"                % "3.1.4"         % Test,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
    )
  )
  .jvmSettings("org.scala-js" %% "scalajs-stubs" % "1.0.0" % "provided")
  .jsSettings(scalaJsUseMainModuleInitializer := true)
  .enablePlugins(PlayScala)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "pty.nfvd.app.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "pty.nfvd.app.binders._"
