import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._
import sbt._

name := """callerid"""

organization := "uk.co.unclealex"

scalaVersion := "2.11.7"

lazy val root = (project in file(".")).enablePlugins(JavaServerAppPackaging, PlayScala)

routesGenerator := InjectedRoutesGenerator

/* Dependencies */

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.1.3",
  "io.argonaut" %% "argonaut" % "6.1")

/* Play */
libraryDependencies ++= Seq(jdbc, cache, ws, evolutions, specs2 % Test)

/* Database */
libraryDependencies ++= Seq("", "-evolutions").map(suffix => "com.typesafe.play" %% s"play-slick$suffix" % "1.0.1")

libraryDependencies += "org.postgresql" % "postgresql" % "9.4-1202-jdbc42"

/* Dependency Injection */
libraryDependencies ++= Seq("play" -> "0.5.8", "akka" -> "0.5.6").map(kv => "org.scaldi" %% s"scaldi-${kv._1}" % kv._2)

/* Testing */
libraryDependencies ++= Seq("core", "mock", "matcher-extra", "analysis", "junit").map(
  suffix => "org.specs2" %% s"specs2-$suffix" % "3.6.4" % "test")

/* Logging */
libraryDependencies ++= Seq("com.typesafe.scala-logging" %% "scala-logging" % "3.1.0", "ch.qos.logback" % "logback-classic" % "1.1.3")

/* Squeezebox */
libraryDependencies += "com.google.http-client" % "google-http-client" % "1.20.0"

/* Akka */
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.12"

/* Time */
libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.2.0"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

scalacOptions in Test ++= Seq("-Yrangepos")

/* Packaging */
maintainer in Linux := "Alex Jones <alex.jones@unclealex.co.uk>"

packageSummary in Linux := "Show who's calling"

packageDescription := "Show who's calling"

daemonUser in Linux := "callerid"

debianPackageDependencies in Debian ++= Seq("logitechmediaserver", "ser2net")

javaOptions in Universal += "-Dhttp.port=9001"

/* Releases */
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies, // : ReleaseStep
  inquireVersions, // : ReleaseStep
  runTest, // : ReleaseStep
  setReleaseVersion, // : ReleaseStep
  commitReleaseVersion, // : ReleaseStep, performs the initial git checks
  tagRelease, // : ReleaseStep
  releaseStepCommand("debian:packageBin"), // : ReleaseStep, build deb file.
  setNextVersion, // : ReleaseStep
  commitNextVersion, // : ReleaseStep
  pushChanges // : ReleaseStep, also checks that an upstream branch is properly configured
)
