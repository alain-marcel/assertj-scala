name := "assertj-scala"

version := "0.1"

scalaVersion := "2.12.8"

allDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0",
  
  "io.circe" %% "circe-core" % "0.11.1",
  "io.circe" %% "circe-generic" % "0.11.1",
  "io.circe" %% "circe-parser" % "0.11.1",
  "io.circe" %% "circe-java8" % "0.11.1",
  
  "org.assertj" % "assertj-core" % "3.12.2",
  "com.jayway.jsonpath" % "json-path" % "2.4.0",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.8",
  "org.skyscreamer" % "jsonassert" % "1.5.0",

  "org.scalatest" %% "scalatest" % "3.0.4" % "test"
)