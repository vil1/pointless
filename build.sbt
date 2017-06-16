organization := "io.kanaka"

name := "pointless"

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.0"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % "2.12.0",
  "com.chuusai" %% "shapeless" % "2.3.2",
  "org.typelevel" %% "cats" % "0.8.1"
)
