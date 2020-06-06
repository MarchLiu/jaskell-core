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


  override def apply[S <: State[Char]](s: S): Expression = {
    val re = parser(s)
    new N(re.toDouble)
  }
}
