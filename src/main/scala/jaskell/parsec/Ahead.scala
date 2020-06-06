package jaskell.parsec

import java.io.EOFException

/**
 * Ahead use a parser to parse state, return the result and rollback whatever
 *
 * @author mars
 * @version 1.0.0
 */
class Ahead[T, E](var parser: Parsec[T, E]) extends Parsec[T, E] {

  override def ask(s: State[E]): Either[Exception, T] = {
    val tran = s.begin()
    val result = parser ? s
    s.rollback(tran)
    result
  }
}

object Ahead {
  def apply[T, E](parser: Parsec[T, E]): Ahead[T, E] = new Ahead[T, E](parser)
}
