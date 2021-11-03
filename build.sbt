name    := "kudago-parser"
version := "0.1"

scalaVersion := "2.13.6"

scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:postfixOps"
)

libraryDependencies ++= Seq(
  Dependencies.catsCore,
  Dependencies.catsEffect,
  Dependencies.doobie,
  Dependencies.pureConfig,
  Dependencies.http4s
).flatten
