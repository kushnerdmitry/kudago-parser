package kudagoparser.config

import cats.effect.IO
import pureconfig.ConfigSource
import pureconfig.generic.auto._

case class IOConfig(
    kudagoBaseUrl: String,
    database: DatabaseConfig
)

case class DatabaseConfig(
    driver: String,
    connectionUrl: String,
    userName: String,
    password: String
)

object IOConfig {
  def loadIO: IO[IOConfig] =
    IO(ConfigSource.resources("application.conf").loadOrThrow[IOConfig])
}
