package jaskell.expression.parsers

import jaskell.Monad.Implicits._
import jaskell.expression.{Expression, Quote}
import jaskell.parsec.{Parsec, SkipWhitespaces, State}

import scala.util.Try

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/03 12:13
 */
class Q extends Parsec[Char, Expression] {
  import jaskell.parsec.Txt.{skipWhiteSpaces, ch}
  import jaskell.Monad.Implicits._
  import jaskell.parsec.Parsec.Implicits._

  lazy val p = new Parser
  val skips: SkipWhitespaces = skipWhiteSpaces
  val parser: Parsec[Char, Expression] = (ch('(') *> skips) *> p <* (skips *> ch(')'))

  override def apply(s: State[Char]): Try[Expression] = {
    parser ? s map {new Quote(_)}
  }
}
