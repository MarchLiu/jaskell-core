package jaskell.expression.parsers

import jaskell.expression.{Expression, Sub}
import jaskell.parsec.Txt.skipWhiteSpaces
import jaskell.parsec.{Parsec, SkipWhitespaces, State}

import scala.util.Try

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/02 21:44
 */
class S(val prev: Expression) extends Parsec[Char, Expression] {

  import jaskell.parsec.Txt.ch
  val skips: SkipWhitespaces = skipWhiteSpaces
  val op: Parsec[Char, Unit] = skips >> ch('-') >> skips

  lazy val next = new Parser

  override def ask(s: State[Char]): Try[Expression] = {
    for {
      _ <- op ? s
      exp <- next ? s
    } yield {new Sub(prev, exp)}
  }
}
