package kudagoparser.client

import cats.effect.IO
import org.http4s.blaze.client.BlazeClientBuilder
import scala.concurrent.ExecutionContext.global

object Client {
  def createResource() = {
    BlazeClientBuilder[IO](global).resource
  }
}
