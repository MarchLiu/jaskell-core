package jaskell.parsec

import scala.util.{Success, Try}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/12 18:53
 */
class Letter extends Parsec [Char, Char]{
  override def apply(s: State[Char]): Try[Char] = s.next() flatMap  { v =>
    if(v.isLetter){
      Success(v)
    } else {
      s.trap(s"expect a letter but get $v")
    }
  }
}

object Letter {
  def apply(): Letter = new Letter()
}