enablePlugins(JmhPlugin)

name := "magnum"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.3.1-RC7"
libraryDependencies ++= {
  Seq(
    "com.h2database" % "h2" % "2.2.222",
    "com.typesafe" % "config" % "1.4.2",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
    "ch.qos.logback" % "logback-classic" % "1.4.11",
    "org.scalatest" %% "scalatest" % "3.2.16" % Test
  )
}