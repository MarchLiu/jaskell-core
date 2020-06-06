package jaskell.parsec

import java.io.EOFException

/**
 * trait of Parsec parsers.
 *
 * @author mars
 * @version 1.0.0
 */
trait Parsec[T, E] {
  @throws[EOFException]
  @throws[ParsecException]
  def apply(s: State[E]): T = {
    ask(s) match {
      case Right(result) => result
      case Left(error) => throw error
    }
  }

  def parse(s: State[E]): T = {
    ask(s) match {
      case Right(result) => result
      case Left(error) => throw error
    }
  }

  def !(s: State[E]): T = {
    ask(s) match {
      case Right(result) => result
      case Left(error) => throw error
    }
  }

  def ask(s: State[E]): Either[Exception, T]

  def ask(s: Seq[E]): Either[Exception, T] = {
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

  def `<|>`(parsec: Parsec[T, E]): Parsec[T, E] = new Choice(Seq(this, parsec))

  def `<?>`(message: String): Parsec[T, E] = (s: State[E]) => {
    this ask s orElse(Left(new ParsecException(s.status, message)))
  }

  def >>[O](p: Parsec[O, E]): Parsec[O, E] = (s: State[E]) => {
    this ask s flatMap {_ => p ask s}
  }

  def >>=[O](binder: T => Parsec[O, E]): Parsec[O, E] = (s: State[E]) => {
    this ask s flatMap {value => binder(value) ask s}
  }

  def ? (s: State[E]): Either[Exception, T] = ask(s)

  def ? (content: Seq[E]): Either[Exception, T] = {
    val st = State(content)
    this ask st
  }
}

object Parsec {
  def apply[T, E](parser: State[E] => Either[Exception, T]): Parsec[T, E] = parser(_)

}

