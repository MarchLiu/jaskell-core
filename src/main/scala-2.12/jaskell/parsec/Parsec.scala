package jaskell.parsec

import jaskell.Monad

import scala.language.implicitConversions
import scala.util.{Failure, Success, Try}

/**
 * trait of Parsec parsers.
 *
 * @author mars
 * @version 1.0.0
 */
trait Parsec[E, +T] {

  def apply(s: State[E]): Try[T]

  def parse(s: State[E]): T = {
    ask(s) match {
      case Success(result) => result
      case Failure(error) => throw error
    }
  }

  def !(s: State[E]): T = {
    ask(s) match {
      case Success(result) => result
      case Failure(error) => throw error
    }
  }

  def ask(s: State[E]): Try[T] = apply(s)

  def ask(s: Seq[E]): Try[T] = {
    ask(State(s))
  }

  def option(s: State[E]): Option[T] = {
    apply(s).toOption
  }

  def `<|>`[U >: T](parsec: Parsec[E, U]): Parsec[E, U] = new Choice(Seq(this, parsec))

  def `<?>`[U >: T](message: String): Parsec[E, U] = (s: State[E]) => {
    this ask s orElse s.trap(message)
  }

  def ?(s: State[E]): Try[T] = ask(s)

  def ?(s: Seq[E]): Try[T] = ask(s)

  // Monad
  val self: Parsec[E, T] = this

  def map[B](f: T => B): Parsec[E, B] = (s: State[E]) => self(s).map(f)

  def <:>[B](f: T => B): Parsec[E, B] = self.map(f)

  def flatMap[B](f: T => Parsec[E, B]): Parsec[E, B] = (s: State[E]) =>
    for {
      a <- self(s)
      func = f(a)
      x <- func(s)
    } yield x


  def liftA2[B, C](f: (T, B) => C): Parsec[E, B] => Parsec[E, C] = (p: Parsec[E, B]) => (s: State[E]) =>
    for {
      a <- self(s)
      b <- p(s)
    } yield f(a, b)

  def <*>[B](f: T => B): Parsec[E, B] = (s: State[E]) =>
    for {
      a <- self(s)
    } yield f(a)

  def *>[B](mb: Parsec[E, B]): Parsec[E, B] = for {
    _ <- self
    re <- mb
  } yield re

  def <*[_](mb: Parsec[E, _]): Parsec[E, T] = for {
    re <- self
    _ <- mb
  } yield re

  def >>=[B](f: T => Parsec[E, B]): Parsec[E, B] = flatMap(f)

  def >>[B](m: Parsec[E, B]): Parsec[E, B] = for {
    _ <- self
    re <- m
  } yield re

}

object Parsec {
  def apply[E, T](parser: State[E] => Try[T]): Parsec[E, T] = parser(_)

  object Implicits {
    implicit def toFlatMapper[E, T, O](binder: Binder[E, T, O]): (T) => Parsec[E, O] = binder.apply

    implicit def mkMonad: Monad[({type P[A] = Parsec[Char, A]})#P] =
      new Monad[({type P[A] = Parsec[Char, A]})#P] {
        override def pure[A](element: A): Parsec[Char, A] = Return(element)

        override def fmap[A, B](m: Parsec[Char, A], f: A => B): Parsec[Char, B] = m.ask(_).map(f)

        override def flatMap[A, B](m: Parsec[Char, A], f: A => Parsec[Char, B]): Parsec[Char, B] = state => for {
          a <- m.ask(state)
          b <- f(a).ask(state)
        } yield b
      }
  }
}

