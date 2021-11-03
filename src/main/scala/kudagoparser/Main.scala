package kudagoparser

import cats.effect.{ExitCode, IO, IOApp}
import kudagoparser.client.Client
import kudagoparser.config.IOConfig

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] =
    for {
      config <- IOConfig.loadIO
      client = Client.createResource()
    } yield ExitCode.Success
}
