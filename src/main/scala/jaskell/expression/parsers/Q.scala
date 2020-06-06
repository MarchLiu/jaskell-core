package jaskell.expression.parsers

import jaskell.expression.{Expression, Quote}
import jaskell.parsec.{Parsec, SkipWhitespaces, State}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/03 12:13
 */
class Q extends Parsec[Expression, Char] {
  import jaskell.parsec.Combinator.between
  import jaskell.parsec.Txt.{skipWhiteSpaces, ch}

  lazy val p = new Parser
  val skips: SkipWhitespaces = skipWhiteSpaces
  val parser: Parsec[Expression, Char] = between(ch('(') >> skips, skips >> ch(')'), p)

  override def ask(s: State[Char]): Either[Exception, Expression] = {
    parser ? s map {new Quote(_)}
  }
}
