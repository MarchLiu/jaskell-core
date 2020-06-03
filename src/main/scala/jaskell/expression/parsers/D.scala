package jaskell.expression.parsers

import jaskell.expression.{Divide, Expression}
import jaskell.parsec.{Ch, Parsec, SkipWhitespaces, State}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/02 21:44
 */
class D(val prev: Expression) extends Parsec[Expression, Char] {

  import jaskell.parsec.Txt.{ch, skipWhiteSpaces}

  val op: Ch = ch('/')
  val skip: SkipWhitespaces = skipWhiteSpaces
  val next = new Parser

  override def apply[S <: State[Char]](s: S): Expression = {
    skip(s)
    op(s)
    skip(s)
    new Divide(prev, next(s))
  }
}
