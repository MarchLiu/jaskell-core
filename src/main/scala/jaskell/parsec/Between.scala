package jaskell.parsec

import java.io.EOFException

import jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 13:43
 */
class Between[T, E](val open: Parsec[_, E], val close: Parsec[_, E], val parsec: Parsec[T, E])
  extends Parsec[T, E] {
  @throws[EOFException]
  @throws[ParsecException]
  override def apply[S <: State[E]](s: S): T = {
    open(s)
    val re = parsec(s)
    close(s)
    re
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

