package jaskell.parsec

import scala.util.{Success, Try}

/**
 * Digit parser return char if it is a digit
 *
 * @author mars
 * @version 1.0.0
 */
class Digit extends Parsec[Char, Char] {

  override def ask(s: State[Char]): Try[Char] = {
    s.next() flatMap { re =>
      if (re.isDigit) {
        Success(re)
      } else {
        s.trap(s"Expect $re is digit.")
      }
    }
  }
}

object Digit {
  def apply(): Digit = new Digit()
}