package jaskell.expression.parsers

import jaskell.expression.{Expression, Product}
import jaskell.parsec.Txt.skipWhiteSpaces
import jaskell.parsec.{Parsec, SkipWhitespaces, State}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/02 21:44
 */
class P(val prev: Expression) extends Parsec[Expression, Char] {

  import jaskell.parsec.Txt.ch

  val skips: SkipWhitespaces = skipWhiteSpaces
  val op: Parsec[Unit, Char] = skips >> ch('*') >> skips
  val next = new Parser

  override def apply[S <: State[Char]](s: S): Expression = {
    op(s)
    new Product(prev, next(s))
  }
}
