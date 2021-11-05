package kudagoparser

import cats.effect.{ContextShift, ExitCode, IO, IOApp}
import kudagoparser.client.HttpClient
import kudagoparser.config.IOConfig
import kudagoparser.db.{DatabaseStore, DatabaseTransactor}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    implicit val cs: ContextShift.type        = ContextShift
    implicit val ec: ExecutionContextExecutor = ExecutionContext.global
    for {
      config <- IOConfig.loadIO
      clientResource = HttpClient(config.kudagoBaseUrl)
      transactor <- DatabaseTransactor.create(config.database)
      dbStore = new DatabaseStore(transactor)
      _ <- getAndSaveKudagoEntities(config, clientResource, dbStore)
    } yield ExitCode.Success
  }

  def getAndSaveKudagoEntities(
      config: IOConfig,
      httpClient: HttpClient,
      dbStore: DatabaseStore
  ): IO[Unit] = {
    getAndSaveCategories(httpClient, dbStore) *>
      // todo: parse entities
      IO.sleep(config.parsingDelay) *>
      getAndSaveKudagoEntities(config, httpClient, dbStore)
  }

  def getAndSaveCategories(httpClient: HttpClient, dbStore: DatabaseStore): IO[Unit] = {
    for {
      eventCategories <- httpClient.getEventCategories
      _               <- dbStore.saveEventCategories(eventCategories)
      placeCategories <- httpClient.getPlaceCategories
      _               <- dbStore.savePlaceCategories(placeCategories)
    } yield ()
  }
}
