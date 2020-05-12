package jaskell.parsec

import java.io.EOFException

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 14:32
 */
class Digit extends Parsec[Char, Char] {
  @throws[EOFException]
  @throws[ParsecException]
  override def apply[S <: State[Char]](s: S): Char =  {
    val re = s.next()
    if (re.isDigit){
     re
    } else {
      throw new ParsecException(s.status, s"Expect $re is digit.")
    }
  }
}

object Digit {
  def apply(): Digit = new Digit()
}