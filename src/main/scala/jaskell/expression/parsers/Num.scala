package jaskell.expression.parsers

import jaskell.expression._
import jaskell.parsec.{Decimal, Parsec, ScNumber, State}

import scala.util.Try

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/02 21:41
 */
class Num extends Parsec[Char, Expression] {

  import jaskell.parsec.Txt.scNumber

  val parser: ScNumber = scNumber

  override def ask(s: State[Char]): Try[Expression] = {
    parser ? s map {n => new N(n.toDouble)}
  }
}
