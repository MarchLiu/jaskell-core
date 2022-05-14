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

}

object Parsec {
  def apply[E, T](parser: State[E] => Try[T]): Parsec[E, T] = parser(_)

  implicit def toFlatMapper[E, T, O](binder: Binder[E, T, O]): (T)=>Parsec[E, O] = binder.apply

  implicit def mkMonad[T]: Monad[({type P[A] = Parsec[T, A]})#P] =
    new Monad[({type P[A] = Parsec[T, A]})#P] {
      override def pure[A](element: A): Parsec[T, A] = Return(element)

      override def fmap[A, B](m: Parsec[T, A], f: A => B): Parsec[T, B] = m.ask(_).map(f)

      override def flatMap[A, B](m: Parsec[T, A], f: A => Parsec[T, B]): Parsec[T, B] = state => for {
        a <- m.ask(state)
        b <- f(a).ask(state)
      } yield b
    }

}

