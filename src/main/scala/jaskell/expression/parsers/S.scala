package jaskell.expression.parsers

import jaskell.expression.{Expression, Sub}
import jaskell.parsec.Txt.skipWhiteSpaces
import jaskell.parsec.{Ch, Parsec, SkipWhitespaces, State}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/02 21:44
 */
class S(val prev: Expression) extends Parsec[Expression, Char] {

  import jaskell.parsec.Txt.ch
  val op: Ch = ch('-')
  val skip: SkipWhitespaces = skipWhiteSpaces

  lazy val next = new Parser

  override def apply[St <: State[Char]](s: St): Expression =  {
    skip(s)
    op(s)
    skip(s)
    new Sub(prev, next(s))
  }
}
