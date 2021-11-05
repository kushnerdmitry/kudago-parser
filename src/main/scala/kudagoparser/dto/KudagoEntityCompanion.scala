package kudagoparser.dto

import cats.effect.IO
import org.http4s.EntityDecoder

trait KudagoEntityCompanion[T <: KudagoEntity] {
  implicit val decoder: EntityDecoder[IO, List[T]]
}
