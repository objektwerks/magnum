enablePlugins(JmhPlugin)

name := "magnum"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.3.1"
libraryDependencies ++= {
  Seq(
    "com.augustnagro" %% "magnum" % "1.1.0",
    "com.h2database" % "h2" % "2.2.222",
    "com.typesafe" % "config" % "1.4.2",
    "ch.qos.logback" % "logback-classic" % "1.4.11",
    "org.scalatest" %% "scalatest" % "3.2.16" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)
fork in run := true
