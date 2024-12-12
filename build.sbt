enablePlugins(JmhPlugin)

name := "magnum"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.6.2"
libraryDependencies ++= {
  Seq(
    "com.augustnagro" %% "magnum" % "1.3.1",
    "com.h2database" % "h2" % "2.3.232",
    "com.typesafe" % "config" % "1.4.3",
    "ch.qos.logback" % "logback-classic" % "1.5.12",
    "org.scalatest" %% "scalatest" % "3.2.19" % Test
  )
}
scalacOptions ++= Seq(
  "-Wall"
)
