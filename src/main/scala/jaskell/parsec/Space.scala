package jaskell.parsec

import java.io.EOFException

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 */
class Space extends Parsec[Char, Char] {

  override def ask(s: State[Char]): Either[Exception, Char] = {
    s.next() flatMap { re =>
      if(re.isSpaceChar) {
        Right(re)
      } else {
        s.trap(s"Expect $re is space.")
      }
    }
  }
}

object Space {
  def apply(): Space = new Space()
}
