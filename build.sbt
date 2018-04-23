name := "scala_bootcamp"

version := "1.0_SNAPSHOT"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.0.5" % "test",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test")

scalacOptions ++= Seq("-deprecation", "-feature")

EclipseKeys.withSource := true

EclipseKeys.withJavadoc := true
