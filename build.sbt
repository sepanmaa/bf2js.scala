lazy val root = (project in file (".")).
  enablePlugins(ScalaJSPlugin).
  settings(
    name := "bf2js",
    version := "0.1",
    scalaVersion := "2.11.6",
    libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % "0.8.0"
  )

skip in packageJSDependencies := false