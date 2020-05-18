package com.kubukoz.http4s

import org.http4s.Http
import org.http4s.dsl.Http4sDsl2
import cats.~>
import cats.Applicative
import cats.Defer
import cats.SemigroupK
import cats.implicits._
import org.http4s.headers.Location

object RickrollMiddleware {

  def default[F[_]: Defer: Applicative: SemigroupK, G[_]](http: Http[F, G])(lift: G ~> F): Http[F, G] = {
    val dsl = new Http4sDsl2[F, G] {
      val liftG: G ~> F = lift
    }

    import dsl._
    import org.http4s.implicits._

    val roll = PermanentRedirect(Location(uri"https://www.youtube.com/watch?v=dQw4w9WgXcQ"))

    val routes: Http[F, G] = Http {
      case _ -> Root / (".env" | "wp-login.php" | "wp-admin") => roll
      case _ -> Root / "wp-admin" / _                         => roll
    }

    routes <+> http
  }
}
