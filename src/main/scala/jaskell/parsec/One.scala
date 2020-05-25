package jaskell.parsec

import java.io.EOFException

/**
 * One just take state.next, It maybe throw eof exception.
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/08 22:28
 */
class One [E] extends Parsec[E, E]{
  @throws[EOFException]
  override def apply[S <: State[E]](s: S): E = s.next()
}

object One {
  def apply[E](): One[E] = new One()
}