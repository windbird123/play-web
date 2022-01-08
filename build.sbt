ThisBuild / organization := "com.github.windbird"
ThisBuild / scalaVersion := "2.12.11"
ThisBuild / version := "1.0.0-SNAPSHOT"

lazy val root = (project in file("."))
  .aggregate(server, client, shared.jvm, shared.js)

lazy val server = (project in file("server"))
  .settings(
    scalaJSProjects := Seq(client),
    Assets / pipelineStages := Seq(scalaJSPipeline),
    pipelineStages := Seq(digest, gzip),
    // triggers scalaJSPipeline when using compile or continuous compilation
    Compile / compile := ((Compile / compile) dependsOn scalaJSPipeline).value,
    libraryDependencies ++= Seq(
      "com.vmunier" %% "scalajs-scripts" % "1.1.4",
      guice,
      ws
    )
  )
  .enablePlugins(PlayScala)
  .dependsOn(shared.jvm)

lazy val client = (project in file("client"))
  .settings(
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "org.scala-js"      %%% "scalajs-dom"   % "1.1.0",
//      "in.nvilla"         %%% "monadic-html"  % "0.4.1",
      "org.querki"        %%% "jquery-facade" % "2.0",
      "com.typesafe.play" %%% "play-json"     % "2.9.2",
      "tech.sparse"       %%% "trail"         % "0.3.1",
      "com.raquo"    %%% "laminar"     % "0.13.1",
      "com.raquo"    %%% "waypoint"    % "0.4.2"
    )
  )
  .enablePlugins(ScalaJSPlugin, ScalaJSWeb)
  .dependsOn(shared.js)

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .jsConfigure(_.enablePlugins(ScalaJSWeb))
  .jsSettings()
