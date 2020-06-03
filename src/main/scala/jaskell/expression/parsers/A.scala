package jaskell.expression.parsers

import jaskell.expression.{Add, Expression}
import jaskell.parsec.Txt.skipWhiteSpaces
import jaskell.parsec.{Ch, Parsec, SkipWhitespaces, State}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/02 21:44
 */
class A(val prev: Expression) extends Parsec[Expression, Char] {

  import jaskell.parsec.Txt.ch
  val op: Ch = ch('+')
  val skip: SkipWhitespaces = skipWhiteSpaces

  lazy val next = new Parser

  override def apply[S <: State[Char]](s: S): Expression =  {
    skip(s)
    op(s)
    skip(s)
    new Add(prev, next(s))
  }
}
