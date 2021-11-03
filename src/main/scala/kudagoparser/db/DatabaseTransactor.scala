package kudagoparser.db

import cats.effect.IO
import doobie.Transactor
import kudagoparser.config.DatabaseConfig

object DatabaseTransactor {
  def createResource(config: DatabaseConfig)(implicit cs: ContextShift[IO]) = {
    Transactor.fromDriverManager[IO](
      config.driver,
      config.connectionUrl,
      config.userName,
      config.password
    )
  }
}
