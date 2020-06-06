package jaskell.expression.parsers

import jaskell.expression._
import jaskell.parsec.{Decimal, Parsec, ScNumber, State}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/02 21:41
 */
class Num extends Parsec[Expression, Char] {

  import jaskell.parsec.Txt.scNumber

  val parser: ScNumber = scNumber

  override def ask(s: State[Char]): Either[Exception, Expression] = {
    parser ? s map {n => new N(n.toDouble)}
  }
}
