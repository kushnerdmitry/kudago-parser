package kudagoparser.client

import cats.effect.{ContextShift, IO}
import com.typesafe.scalalogging.LazyLogging
import kudagoparser.dto.{Category, KudagoEntity}
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.{EntityDecoder, Uri}

import scala.concurrent.ExecutionContext

class HttpClient(
    val kudagoBaseUrl: String
)(implicit cs: ContextShift[IO], ec: ExecutionContext)
    extends LazyLogging {
  private val clientResource = BlazeClientBuilder[IO](ec).resource

  private val uri = Uri.unsafeFromString(kudagoBaseUrl)

  def getEventCategories: IO[List[Category]] = {
    logger.info("Getting event categories...")
    getCategories("event-categories")(Category.decoder)
  }

  def getPlaceCategories: IO[List[Category]] = {
    logger.info("Getting place categories...")
    getCategories("place-categories")(Category.decoder)
  }

  private def getCategories[E <: KudagoEntity](path: String)(
      implicit d: EntityDecoder[IO, List[E]]
  ) = {
    val queryParams = Map(
      "lang"   -> List("ru"),
      "fields" -> List("name", "slug")
    )

    GETRequest[E](path, queryParams)
  }

  private def GETRequest[E <: KudagoEntity](
      path: String,
      params: Map[String, List[String]] = Map.empty
  )(
      implicit d: EntityDecoder[IO, List[E]]
  ): IO[List[E]] = {
    clientResource
      .use { client =>
        client.expect[List[E]](uri.addPath(path) =? params)
      }
      .attempt
      .flatMap {
        case Left(e) =>
          logger.error(s"GET $path error")
          IO.raiseError(e)
        case Right(list) =>
          logger.info(s"Got entities from $path")
          IO(list)
      }
  }
}

object HttpClient {
  def apply(kudagoBaseUrl: String)(
      implicit cs: ContextShift[IO],
      ec: ExecutionContext
  ): HttpClient = {
    new HttpClient(kudagoBaseUrl)
  }
}
