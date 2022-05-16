package jaskell.expression.parsers

import jaskell.expression.{Divide, Expression}
import jaskell.parsec.{Parsec, SkipWhitespaces, State}

import scala.util.Try

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 */
class D(val prev: Expression) extends Parsec[Char, Expression] {
  import jaskell.Monad.Implicits._
  import jaskell.parsec.Parsec.Implicits._
  import jaskell.parsec.Txt.{ch, skipWhiteSpaces}

  val skips: SkipWhitespaces = skipWhiteSpaces
  val op: Parsec[Char, Unit] = skips >> ch('/') >> skips
  val next = new Parser

  override def apply(s: State[Char]): Try[Expression] = {
    for {
      _ <- op ? s
      exp <- next ? s
    } yield {new Divide(prev, exp)}
  }
}
