import sbt._

object Dependencies {
  object Versions {
    lazy val http4sVersion     = "0.22.7"
    lazy val doobieVersion     = "0.13.4"
    lazy val pureConfigVersion = "0.16.0"
    lazy val catsCoreVersion   = "2.6.1"
    lazy val catsEffectVersion = "2.5.1"
    lazy val loggingVersion    = "3.9.4"
    lazy val circeVersion      = "0.14.1"
  }

  import Versions._

  lazy val pureConfig = Seq(
    "com.github.pureconfig" %% "pureconfig" % pureConfigVersion
  )

  lazy val logging = Seq(
    "com.typesafe.scala-logging" %% "scala-logging" % loggingVersion
  )

  lazy val catsCore = Seq(
    "org.typelevel" %% "cats-core" % catsCoreVersion
  )

  lazy val catsEffect = Seq(
    "org.typelevel" %% "cats-effect" % catsEffectVersion
  )

  lazy val http4s = Seq(
    "org.http4s" %% "http4s-dsl"          % http4sVersion,
    "org.http4s" %% "http4s-blaze-client" % http4sVersion,
    "org.http4s" %% "http4s-circe"        % http4sVersion
  )

  lazy val doobie = Seq(
    "org.tpolecat" %% "doobie-core"     % doobieVersion,
    "org.tpolecat" %% "doobie-postgres" % doobieVersion
  )

  lazy val circe = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser"
  ).map(_ % circeVersion)

}
