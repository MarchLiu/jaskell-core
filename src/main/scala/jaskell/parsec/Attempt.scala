package jaskell.parsec

import scala.util.{Failure, Success, Try}

/**
 * The parser try p behaves like parser p, except that it pretends that it hasn't consumed any input
 * when an error occurs.
 *
 * This combinator is used whenever arbitrary look ahead is needed. Since it pretends that it hasn't
 * consumed any input when p fails, the (<|>) combinator will try its second alternative even when the
 * first parser failed while consuming input.
 *
 * @author Mars Liu
 * @version 1.0.0
 */
class Attempt[E, T](val parsec: Parsec[E, T]) extends Parsec[E, T] {

  override def apply(s: State[E]): Try[T] = {
    val tran = s.begin()
    parsec ? s match {
      case right: Success[T] =>
        s commit tran
        right
      case left: Failure[T] =>
        s rollback tran
        left
    }
  }
}

object Attempt {
  def apply[E, T](parsec: Parsec[E, T]): Attempt[E, T] = new Attempt(parsec)
}
