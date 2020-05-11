package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/08 22:28
 */
trait Parsec[T, E] {
  @throws[EofException]
  @throws[ParsecException]
  def apply[S <: State[E]](s: S): T

  def either[S <: State[E]](s: S): Either[Exception, T] = {
    try Right(apply(s))
    catch {
      case e: Exception =>
        Left(e)
    }
  }

  def opt[S <: State[E]](s: S): Option[T] = {
    try {
      Some(apply(s))
    } catch {
      case _: Exception =>
        Option.empty[T]
    }
  }
}

object Parsec {
  def apply[T, E](parser: State[E] => T): Parsec[T, E] = {
    new Parsec[T, E] {
      override def apply[St <: State[E]](s: St): T = parser(s)
    }
  }
}

