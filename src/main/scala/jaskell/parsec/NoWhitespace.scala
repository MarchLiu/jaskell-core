package jaskell.parsec

import scala.util.{Success, Try}

/**
 * NoWhitespace success if the char is't any whitespace.
 *
 * @author mars
 * @version 1.0.0
 */
class NoWhitespace extends Parsec [Char, Char]{
  override def ask(s: State[Char]): Try[Char] = {
    s.next() flatMap { c =>
      if(c.isWhitespace){
        s.trap(s"expect a char not whitespace but get $c")
      } else {
        Success(c)
      }
    }
  }
}

object NoWhitespace {
  def apply(): NoWhitespace = new NoWhitespace()
}