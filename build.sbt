lazy val projectName    = "kudago-parser"
lazy val projectVersion = "0.1.0"

name := projectName

version := projectVersion

scalaVersion := "2.13.6"

dockerRepository      := Some("kushnerdmitry")
packageName in Docker := projectName
dockerBaseImage       := "openjdk:8"
version in Docker     := projectVersion

scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:postfixOps"
  // "-Ypartial-unification"
)

libraryDependencies ++= Seq(
  Dependencies.logging,
  Dependencies.circe,
  Dependencies.catsCore,
  Dependencies.catsEffect,
  Dependencies.doobie,
  Dependencies.pureConfig,
  Dependencies.http4s
).flatten

resolvers ++= Seq(
  "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"
)
