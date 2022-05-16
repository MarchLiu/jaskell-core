package jaskell

import scala.util.{Try, Success}

trait Applicative[F[_]] extends Functor[F] {
  extension[A, B] (fa: F[A => B])
    def <*>(fb: F[A]): F[B] = for {
      f <- fa
      a <- fb
    } yield f(a)

    def <|>(fb: F[A => B]): F[A => B] =
      for {
        a <- fa
        b <- fb
      } yield (arg: A) => try {
        a(arg)
      } catch {
        _ => b(arg)
      }

  extension[A, B] (x: F[A])
    def *>(bx: F[B]) = for {
      _ <- x
      b <- bx
    } yield b

    def <*(bx: F[B]) = for {
      a <- x
      _ <- bx
    } yield a

  extension[A, B, C] (fx: F[(A, B) => C])
    def liftA2(ax: F[A], bx: F[B]): F[C] = for {
      f <- fx
      a <- ax
      b <- bx
    } yield f(a, b)
}

given tryApplictive[Arg]: Applicative[[Result] =>> Arg => Try[Result]] with {
  def pure[T](arg: T): Arg => Try[T] = x => Success(arg)

  extension[A, B] (fa: Arg => Try[A]) {
    def map(f: A => B): Arg => Try[B] = arg => fa(arg).map(f)

    def flatMap(fb: A => Arg => Try[B]): Arg => Try[B] = arg => fa(arg).flatMap(x => fb(x)(arg))
  }

  extension[A] (fa: Arg => Try[A]) {
    def <|>(fb: Arg => Try[A]): Arg => Try[A] = arg => fa(arg).orElse(fb(arg))
  }

}