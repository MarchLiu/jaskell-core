package jaskell

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

trait Functor[F[_]]:
  def pure[A](x: A): F[A]

  extension[A, B] (x: F[A])

  /** The unit value for a monad */
    def map(f: A => B): F[B]
    def fmap(f: A => B): F[B] = x.map(f)
    
    def flatMap(f: A => F[B]): F[B]

    // scala reject the operator <$> , so I use <:> as infix synonym for fmap/map
    def <:> (f: A=> B): F[B] = x.map(f)

given Functor[List] with
  def pure[A](x: A) = List(x)
  extension[A, B] (xs: List[A])
    def map(f: A => B): List[B] =
      xs.map(f) // List already has a `map` method
    def flatMap(f: A => List[B]): List[B] = xs.flatMap(f)

trait SeqU {}

given seqU: SeqU with {
  extension[T] (seq: Seq[Try[T]])
    def flip: Try[Seq[T]] = {
      val failure = seq.filter(_.isFailure)
      if(failure.isEmpty){
        return Success(seq.map(_.get))
      } else {
        return Failure(failure.head.failed.get)
      }
    }
}

