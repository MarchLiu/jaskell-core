package jaskell.parsec

import scala.util.{Success, Try}

/**
 * Newline match \n char
 *
 * @author mars
 * @version 1.0.0
 */
class Newline extends Parsec[Char, Char]{

  override def ask(s: State[Char]): Try[Char] = {
    s.next() flatMap { c =>
      if(c == '\n') {
        Success(c)
      } else {
        s.trap(s"Expect a newline char but get $c")
      }
    }
  }
}

object Newline {
  def apply(): Newline = new Newline()
}
