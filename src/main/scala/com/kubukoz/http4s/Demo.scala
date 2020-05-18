package com.kubukoz.http4s

import cats.effect.IOApp
import cats.effect.{ExitCode, IO}
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.dsl.io._
import org.http4s.HttpRoutes
import org.http4s.implicits._
import scala.concurrent.ExecutionContext
import cats.arrow.FunctionK

object Demo extends IOApp {

  val routes = HttpRoutes.of[IO] {
    case GET -> Root / "hello" => Ok("Hello world!")
  }

  def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO](ExecutionContext.global)
      .withHttpApp(RickrollMiddleware.default(routes.orNotFound)(FunctionK.id))
      .bindLocal(8080)
      .serve
      .compile
      .lastOrError
}
