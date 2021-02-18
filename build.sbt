name := "jaskell-core"

version := "0.6.2"

scalaVersion := "2.13.2"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.1.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test"
libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.31.1" % "test"

ThisBuild / organization := "io.github.marchliu"
ThisBuild / organizationName := "Mars Liu"
ThisBuild / organizationHomepage := Some(url("https://marchliu.github.io/"))

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/MarchLiu/jaskell-core"),
    "scm:git@github.com:MarchLiu/jaskell-core.git"
  )
)
ThisBuild / developers := List(
  Developer(
    id    = "Mars Liu",
    name  = "Liu Xin",
    email = "mars.liu@outlook.com",
    url   = url("https://marchliu.github.io/")
  )
)

ThisBuild / description := "Jaskell Core components Library, written by scala"
ThisBuild / licenses := List("MIT" -> new URL("https://github.com/MarchLiu/jaskell-core/blob/master/LICENSE"))
ThisBuild / homepage := Some(url("https://github.com/MarchLiu/jaskell-core"))

// Remove all additional repository other than Maven Central from POM
ThisBuild / pomIncludeRepository := { _ => false }
ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
ThisBuild / publishMavenStyle := true