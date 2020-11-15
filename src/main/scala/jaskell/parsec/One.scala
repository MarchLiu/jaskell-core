package jaskell.parsec

import scala.util.Try

/**
 * One just take state.next, It maybe throw eof exception.
 *
 * @author mars
 * @version 1.0.0
 */
class One [E] extends Parsec[E, E]{

  override def ask(s: State[E]): Try[E] = s.next()
}

object One {
  def apply[E]: One[E] = new One()
}