package jaskell.expression.parsers

import jaskell.expression.{Divide, Expression}
import jaskell.parsec.{Parsec, SkipWhitespaces, State}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 */
class D(val prev: Expression) extends Parsec[Expression, Char] {

  import jaskell.parsec.Txt.{ch, skipWhiteSpaces}

  val skips: SkipWhitespaces = skipWhiteSpaces
  val op: Parsec[Unit, Char] = skips >> ch('/') >> skips
  val next = new Parser

  override def ask(s: State[Char]): Either[Exception, Expression] = {
    for {
      _ <- op ? s
      exp <- next ? s
    } yield {new Divide(prev, exp)}
  }
}
