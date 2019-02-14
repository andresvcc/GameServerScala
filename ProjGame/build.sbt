name := "Server"
organization := "example.server"
version := "1.0"
scalaVersion := "2.11.7"

libraryDependencies ++= {
  Seq(
    "com.typesafe.akka" %% "akka-http-core-experimental" % "2.0.2"
  )
}