package jaskell.parsec

import scala.util.{Success, Try}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 17:18
 */
class Whitespace extends Parsec [Char, Char]{

  override def ask(s: State[Char]): Try[Char] = {
    s.next() flatMap { c =>
      if(c.isWhitespace){
        Success(c)
      } else {
        s.trap(s"expect a whitespace but get $c")
      }
    }
  }
}

object Whitespace {
  def apply(): Whitespace = new Whitespace()
}