package jaskell.parsec

import java.io.EOFException

import jaskell.parsec

/**
 * Between parse the open parser, and then sub parser, and then close parser, return sub parser result if success.
 *
 * @author mars
 * @version 1.0.0
 */
class Between[T, E](val open: Parsec[_, E], val close: Parsec[_, E], val parsec: Parsec[T, E])
  extends Parsec[T, E] {

  override def ask(s: State[E]): Either[Exception, T] = {
    for {
      _ <- open ? s
      re <- parsec ? s
      _ <- close ? s
    } yield re
  }
}

object Between {

  class Btw[T, E](val open: Parsec[_, E], val close: Parsec[_, E]) {
    def in(parsec: Parsec[T, E]) = new Between[T, E](this.open, this.close, parsec)
  }

  def apply[T, E](open: Parsec[_, E], close: Parsec[_, E]) = new parsec.Between.Btw[T, E](open, close)

  def apply[T, E](open: Parsec[_, E], close: Parsec[_, E], parsec: Parsec[T, E]) =
    new Between[T, E](open, close, parsec)

}

