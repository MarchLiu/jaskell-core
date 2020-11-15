package jaskell.expression

import jaskell.parsec.Fail

import scala.util.{Failure, Try}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/10 13:24
 */
class Parameter(val name: String) extends Expression {
  override def eval(env: Env): Try[Double] = {
    env.get(name).map(_.eval(env)) match {
      case Some(value) => value
      case _ => Failure(new ExpressionException(s"$name not found"))
    }
  }
}
