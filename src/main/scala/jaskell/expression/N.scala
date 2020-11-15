package jaskell.expression

import scala.util.{Success, Try}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/02 21:27
 */
class N(val num: java.lang.Number) extends Expression {

  override def eval(env: Env): Try[Double] = Success(num.doubleValue())
}


object N {
  def apply(n: java.lang.Number): N = new N(n)
}
