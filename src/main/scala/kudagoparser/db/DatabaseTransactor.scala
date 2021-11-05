package kudagoparser.db

import cats.effect.{ContextShift, IO}
import doobie.Transactor
import kudagoparser.config.DatabaseConfig

object DatabaseTransactor {
  def create(config: DatabaseConfig)(implicit cs: ContextShift[IO]) = {
    IO(
      Transactor.fromDriverManager[IO](
        config.driver,
        config.connectionUrl,
        config.userName,
        config.password
      )
    )
  }
}
