package jaskell

import jaskell.parsec.{Parsec, Return}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Try}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2021/02/10 16:05
 */
trait Monad[M[_]] {
  def pure[A](element: A): M[A]

  def fmap[A, B](m: M[A], f: A => B): M[B]

  def flatMap[A, B](m: M[A], f: A => M[B]): M[B]

  def liftA2[A, B, C](f: (A, B) => C): (Monad.MonadOps[A, M], Monad.MonadOps[B, M]) => M[C] = { (ma, mb) =>
    for {
      a <- ma
      b <- mb
    } yield f(a, b)
  }

}

object Monad {
  def apply[M[_]](implicit instance: Monad[M]): Monad[M] = instance
  def apply[M[_]](implicit creator: () => Monad[M]): Monad[M] = creator.apply()

  abstract class MonadOps[A, M[_]](implicit I: Monad[M]) {
    def self: M[A]


    def map[B](f: A => B): M[B] = I.fmap(self, f)

    def <:>[B](f: A => B): M[B] = I.fmap(self, f)

    def flatMap[B](f: A => M[B]): M[B] = I.flatMap(self, f)

    def liftA2[B, C](f: (A, B) => C): (M[B]) => M[C] = m => I.liftA2(f)(self, m)

    def <*>[B](f: A => B): M[A] => M[B] = ma => I.fmap(ma, f)

    def *>[B](mb: M[B]): M[B] = for {
      _ <- self
      re <- mb
    } yield re

    def <*[_](mb: M[_]): M[A] = for {
      re <- self
      _ <- mb
    } yield re

    def >>=[B](f: A => M[B]): M[B] = flatMap(f)

    def >>[B](m: M[B]): M[B] = for {
      _ <- self
      re <- m
    } yield re

  }

  implicit def toMonad[A, M[_]](target: M[A])(implicit I: Monad[M]): MonadOps[A, M] =
    new MonadOps[A, M]() {
      override def self: M[A] = target
    }

  implicit val listMonad: Monad[List] = new Monad[List] {
    override def pure[A](element: A): List[A] = List(element)

    override def fmap[A, B](m: List[A], f: A => B): List[B] = m.map(f)

    override def flatMap[A, B](m: List[A], f: A => List[B]): List[B] = m.flatMap(f)
  }


  implicit val seqMonad: Monad[Seq] = new Monad[Seq] {
    override def pure[A](element: A): Seq[A] = Seq(element)

    override def fmap[A, B](m: Seq[A], f: A => B): Seq[B] = m.map(f)

    override def flatMap[A, B](m: Seq[A], f: A => Seq[B]): Seq[B] = m.flatMap(f)
  }

  implicit val tryMonad: Monad[Try] = new Monad[Try] {
    override def pure[A](element: A): Try[A] = Success(element)

    override def fmap[A, B](m: Try[A], f: A => B): Try[B] = m.map(f)

    override def flatMap[A, B](m: Try[A], f: A => Try[B]): Try[B] = m.flatMap(f)
  }

  implicit def futureMonad(implicit ec: ExecutionContext): Monad[Future] = new Monad[Future] {
    override def pure[A](element: A): Future[A] = Future.successful(element)

    override def fmap[A, B](m: Future[A], f: A => B): Future[B] = m.map(f)

    override def flatMap[A, B](m: Future[A], f: A => Future[B]): Future[B] = m.flatMap(f)
  }
}
