package kudagoparser.config

import cats.effect.IO
import pureconfig.ConfigSource
import pureconfig.generic.auto._

import scala.concurrent.duration.FiniteDuration

case class IOConfig(
    kudagoBaseUrl: String,
    parsingDelay: FiniteDuration,
    database: DatabaseConfig
)

case class DatabaseConfig(
    serverName: String,
    serverPort: Int,
    databaseName: String,
    userName: String,
    password: String
) {
  val driver        = "org.postgresql.Driver"
  val connectionUrl = s"jdbc:postgresql://$serverName:$serverPort/$databaseName"
}

object IOConfig {
  def loadIO: IO[IOConfig] =
    IO(ConfigSource.resources("application.conf").loadOrThrow[IOConfig])
}
