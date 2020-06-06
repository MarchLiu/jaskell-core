package jaskell.parsec

import java.io.EOFException

/**
 * One just take state.next, It maybe throw eof exception.
 *
 * @author mars
 * @version 1.0.0
 */
class One [E] extends Parsec[E, E]{

  override def ask(s: State[E]): Either[Exception, E] = {
    s.next()
  }
}

object One {
  def apply[E](): One[E] = new One()
}