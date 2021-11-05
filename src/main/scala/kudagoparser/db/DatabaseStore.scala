package kudagoparser.db

import cats.effect.IO
import cats.implicits._
import com.typesafe.scalalogging.LazyLogging
import doobie.implicits._
import doobie.postgres.implicits._
import doobie.util.transactor.Transactor
import kudagoparser.dto.Category

import java.util.UUID

class DatabaseStore(dbTransactor: Transactor.Aux[IO, Unit]) extends LazyLogging {
  def savePlaceCategories(
      categories: List[Category]
  ): IO[List[Unit]] = {
    logger.info("Saving place categories...")
    saveCategories("place_categories", categories)
  }

  def saveEventCategories(
      categories: List[Category]
  ): IO[List[Unit]] = {
    logger.info("Saving event categories...")
    saveCategories("event_categories", categories)
  }

  def saveCategories(tableName: String, categories: List[Category]): IO[List[Unit]] = {
    val time = getLocalDateTime

    categories.map { category =>
      val slug = category.slug
      val name = category.name
      sql"INSERT INTO $tableName (slug, name, createdAt, updatedAt) values ($slug, $name, $time, $time) ON CONFLICT (slug) DO NOTHING;"
        .update
        .withUniqueGeneratedKeys[UUID]("id")
        .transact(dbTransactor)
        .attempt
        .flatMap {
          case Left(e) =>
            logger.error(s"Event/place category insert error: ${e.getMessage}")
            IO.raiseError(e)
          case Right(id) =>
            logger.info(s"Inserted event/place category with id: $id")
            IO.unit
        }
    }.sequence
  }

  private def getLocalDateTime = {
    java.time.LocalDateTime.now
  }
}
