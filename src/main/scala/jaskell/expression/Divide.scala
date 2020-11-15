package jaskell.expression

import scala.util.Try

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/02 21:35
 */
class Divide(l: Expression, r: Expression) extends Binary(l, r) {

  override def eval(env: Env): Try[Double] =
    for {
      lv <- left.eval(env)
      rv <- right.eval(env)
    } yield {
      lv / rv
    }

  override def priority: Int = 2
}
