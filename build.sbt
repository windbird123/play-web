ThisBuild / organization := "com.github.windbird"
ThisBuild / scalaVersion := "2.13.8"
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
      "org.scala-js"      %%% "scalajs-dom"   % "2.1.0",
      "com.typesafe.play" %%% "play-json"     % "2.9.2",
      "com.lihaoyi"       %%% "upickle"       % "1.4.4",
      "com.raquo"         %%% "laminar"       % "0.14.5",
      "com.raquo"         %%% "waypoint"      % "0.5.0",
      "org.plotly-scala"  %%% "plotly-render" % "0.8.4"
//      "org.openmole"      %%% "scala-js-plotlyjs" % "1.7.0"
    )
  )
  .enablePlugins(ScalaJSPlugin, ScalaJSWeb)
  .dependsOn(shared.js)

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .jsConfigure(_.enablePlugins(ScalaJSWeb))
  .jsSettings()
