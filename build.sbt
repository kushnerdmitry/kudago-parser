ThisBuild / scalaVersion := "2.13.6"

ThisBuild / scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:postfixOps",
  "-Ypartial-unification"
)

lazy val root = (project in file(".")).settings(
  name    := "kudago-parser",
  version := "0.1",
  libraryDependencies ++= Seq(
    Dependencies.catsCore,
    Dependencies.catsEffect,
    Dependencies.doobie,
    Dependencies.pureConfig,
    Dependencies.http4s
  ).flatten
)
