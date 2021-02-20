package jaskell.expression.parsers

import jaskell.Monad
import jaskell.expression.{Add, Expression}
import jaskell.parsec.Txt.skipWhiteSpaces
import jaskell.parsec.{Parsec, SkipWhitespaces, State}

import scala.util.Try

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 */
class A(val prev: Expression) extends Parsec[Char, Expression] {

  import jaskell.parsec.Txt.ch
  import Monad._
  import jaskell.parsec.Parsec.mkMonad

  val skips: SkipWhitespaces = skipWhiteSpaces
  val op: Parsec[Char, Unit] = skips >> ch('+') >> skips

  lazy val next = new Parser

  override def apply(s: State[Char]): Try[Expression] = {
    for {
      _ <- op ? s
      exp <- next ? s
    } yield {new Add(prev, exp)}
  }
}
