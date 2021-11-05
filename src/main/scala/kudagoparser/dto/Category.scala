package kudagoparser.dto

import cats.effect.IO
import io.circe.generic.auto._
import org.http4s.EntityDecoder
import org.http4s.circe.jsonOf

case class Category(
    slug: String,
    name: String
) extends KudagoEntity

object Category extends KudagoEntityCompanion[Category] {
  implicit val decoder: EntityDecoder[IO, List[Category]] = jsonOf[IO, List[Category]]
}
