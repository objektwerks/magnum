enablePlugins(JmhPlugin)

name := "magnum"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.7.1"
libraryDependencies ++= {
  Seq(
    "com.augustnagro" %% "magnum" % "2.0.0-M2",
    "com.h2database" % "h2" % "2.3.232",
    "com.typesafe" % "config" % "1.4.3",
    "ch.qos.logback" % "logback-classic" % "1.5.18",
    "org.scalatest" %% "scalatest" % "3.2.19" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)
