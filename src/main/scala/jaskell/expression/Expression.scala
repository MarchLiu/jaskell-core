package jaskell.expression

import scala.util.Try

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/02 21:26
 */
trait Expression {
  def eval(env: Env): Try[Double]
  def makeAst: Expression = this
}
