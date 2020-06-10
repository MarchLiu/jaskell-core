package jaskell.expression

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/02 21:26
 */
trait Expression {
  def eval(env: Env): Either[Exception, Double]
  def makeAst: Expression = this
}
