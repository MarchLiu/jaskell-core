package jaskell

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

trait Monad[F[_]] extends Applicative[F] {
  extension[A, B] (x: F[A]) {
    def >>=(f: A => F[B]): F[B] = {
      x.flatMap(f)
    }

    /** The `map` operation can now be defined in terms of `flatMap` */
    def map(f: A => B): F[B] = x.flatMap(f.andThen(pure))

    def >>(y: F[B]): F[B] = for {
      _ <- x
      re <- y
    } yield re
  }
}

object Monad {
  object Implicits {

    given listMonad: Monad[List] with {
      def pure[A](x: A): List[A] = List(x)

      extension[A, B] (xs: List[A]) {
      def flatMap(f: A => List[B]): List[B] =
      xs.flatMap(f) // rely on the existing `flatMap` method of `List`
    }
    }

    given optionMonad: Monad[Option] with {
      def pure[A](x: A): Option[A] = Option(x)

      extension[A, B] (xo: Option[A]) {
      def flatMap(f: A => Option[B]): Option[B] = xo match
      case Some(x) => f(x)
      case None => None
    }
    }

    given tryMonad: Monad[Try] with {
      def pure[A](x: A) = Success(x)

      extension[A, B] (xt: Try[A]) {
      override def map(f: A => B): Try[B] = xt.map(f)
      def flatMap(f: A => Try[B]): Try[B] = xt.flatMap(f)
    }
    }

    given futureMonad(using ec: ExecutionContext): Monad[Future] with {
      def pure[A](x: A): Future[A] = Future.successful(x)

      extension[A, B] (x: Future[A]) {
      override def map(f: A => B): Future[B] = x.map(f)
      def flatMap(f: A => Future[B]): Future[B] = x.flatMap(f)
    }
    }
  }
}
