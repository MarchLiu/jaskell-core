package jaskell.parsec

import scala.util.{Success, Try}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 */
class Space extends Parsec[Char, Char] {

  override def apply(s: State[Char]): Try[Char] = {
    s.next() flatMap { re =>
      if(re.isSpaceChar) {
        Success(re)
      } else {
        s.trap(s"Expect $re is space.")
      }
    }
  }
}

object Space {
  def apply(): Space = new Space()
}
