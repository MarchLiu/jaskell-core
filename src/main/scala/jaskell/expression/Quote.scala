package jaskell.expression

import scala.util.Try

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/02 21:27
 */
class Quote(val exp: Expression) extends Expression {

  override def eval(env: Env): Try[Double] = exp.eval(env)

  override def makeAst: Expression = new Quote(exp.makeAst)
}
