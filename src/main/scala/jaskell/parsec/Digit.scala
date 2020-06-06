package jaskell.parsec

import java.io.EOFException

/**
 * Digit parser return char if it is a digit
 *
 * @author mars
 * @version 1.0.0
 */
class Digit extends Parsec[Char, Char] {

  override def ask(s: State[Char]): Either[Exception, Char] = {
    s.next() flatMap { re =>
      if (re.isDigit) {
        Right(re)
      } else {
        Left(new ParsecException(s.status, s"Expect $re is digit."))
      }
    }
  }
}

object Digit {
  def apply(): Digit = new Digit()
}