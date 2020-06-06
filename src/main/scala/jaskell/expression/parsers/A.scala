package jaskell.expression.parsers

import jaskell.expression.{Add, Expression}
import jaskell.parsec.Txt.skipWhiteSpaces
import jaskell.parsec.{Parsec, SkipWhitespaces, State}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 */
class A(val prev: Expression) extends Parsec[Expression, Char] {

  import jaskell.parsec.Txt.ch
  val skips: SkipWhitespaces = skipWhiteSpaces
  val op: Parsec[Unit, Char] = skips >> ch('+') >> skips

  lazy val next = new Parser

  override def ask(s: State[Char]): Either[Exception, Expression] = {
    for {
      _ <- op ? s
      exp <- next ? s
    } yield {new Add(prev, exp)}
  }
}
