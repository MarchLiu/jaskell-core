package jaskell.parsec

import jaskell.Monad
import jaskell.Monad.MonadOps

import java.io.EOFException
import scala.util.{Failure, Success, Try}

/**
 * trait of Parsec parsers.
 *
 * @author mars
 * @version 1.0.0
 */
trait Parsec[E, T] {
  @throws[EOFException]
  @throws[ParsecException]
  def apply(s: State[E]): T = {
    ask(s) match {
      case Success(result) => result
      case Failure(error) => throw error
    }
  }

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

  def ask(s: State[E]): Try[T]

  def ask(s: Seq[E]): Try[T] = {
    ask(State(s))
  }

  def opt(s: State[E]): Option[T] = {
    try {
      Some(apply(s))
    } catch {
      case _: Exception =>
        Option.empty[T]
    }
  }

  def `<|>`(parsec: Parsec[E, T]): Parsec[E, T] = new Choice(Seq(this, parsec))

  def `<?>`(message: String): Parsec[E, T] = (s: State[E]) => {
    this ask s orElse s.trap(message)
  }
//
//  def >>[O](p: Parsec[E, O]): Parsec[E, O] = (s: State[E]) => {
//    this ask s flatMap { _ => p ask s }
//  }
//
//  def >>=[O](binder: Binder[E, T, O]): Parsec[E, O] = (s: State[E]) => {
//    this ask s flatMap { value => binder(value) ? s }
//  }

  def ?(s: State[E]): Try[T] = ask(s)

  def ?(s: Seq[E]): Try[T] = ask(s)

}

object Parsec {
  def apply[E, T](parser: State[E] => Try[T]): Parsec[E, T] = parser(_)

  implicit def toFlatMapper[E, T, O](binder: Binder[E, T, O]): (T)=>Parsec[E, O] = binder.apply

  implicit def toParsec[E, T, P <: Parsec[E, T]](parsec: P): Parsec[E, T] = parsec.asInstanceOf[Parsec[E, T]]

  implicit val charParsecMonad: Monad[({type P[A] = Parsec[Char, A]})#P] = mkMonad

  def mkMonad[T]: Monad[({type P[A] = Parsec[T, A]})#P] =
    new Monad[({type P[A] = Parsec[T, A]})#P] {
      override def pure[A](element: A): Parsec[T, A] = Return(element)

      override def fmap[A, B](m: Parsec[T, A], f: A => B): Parsec[T, B] = m.ask(_).map(f)

      override def flatMap[A, B](m: Parsec[T, A], f: A => Parsec[T, B]): Parsec[T, B] = state => for {
        a <- m.ask(state)
        b <- f(a).ask(state)
      } yield b
    }
}

