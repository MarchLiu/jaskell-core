name := "jaskell-core"

lazy val scala213 = "2.13.8"
lazy val scala212 = "2.12.15"
lazy val scala211 = "2.11.12"
lazy val scala312 = "3.1.2"
lazy val supportedScalaVersions = List(scala213, scala212, scala211)

version := "0.8.0"
scalaVersion := scala213

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test"
libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.40.1.0" % "test"

crossScalaVersions := supportedScalaVersions

ThisBuild / organization := "io.github.marchliu"
ThisBuild / organizationName := "Mars Liu"
ThisBuild / organizationHomepage := Some(url("https://marchliu.github.io/"))


def scalacOptionsVersion(scalaVersion: String): Seq[String] = {
  Seq(
    "-unchecked",
    "-deprecation",
    "-Xlint",
    "-Xfatal-warnings",
    "-Ywarn-dead-code",
    "-encoding", "UTF-8"
  ) ++(CrossVersion.partialVersion(scalaVersion) match {
    case Some((2, scalaMajor)) if scalaMajor == 11 => Seq("-Xexperimental")
    case _ => Nil
  })
}



ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/MarchLiu/jaskell-core"),
    "scm:git@github.com:MarchLiu/jaskell-core.git"
  )
)
ThisBuild / developers := List(
  Developer(
    id = "Mars Liu",
    name = "Liu Xin",
    email = "mars.liu@outlook.com",
    url = url("https://marchliu.github.io/")
  )
)

ThisBuild / description := "Jaskell Core components Library, written by scala"
ThisBuild / licenses := List("MIT" -> new URL("https://github.com/MarchLiu/jaskell-core/blob/master/LICENSE"))
ThisBuild / homepage := Some(url("https://github.com/MarchLiu/jaskell-core"))

// Remove all additional repository other than Maven Central from POM
// ThisBuild / pomIncludeRepository := { _ => false }
ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
// ThisBuild / publishMavenStyle := true

val appSettings = Seq(
  scalacOptions := scalacOptionsVersion(scalaVersion.value)
)