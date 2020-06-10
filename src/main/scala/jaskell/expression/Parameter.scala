package jaskell.expression

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/10 13:24
 */
class Parameter(val name: String) extends Expression {
  override def eval(env: Env): Either[Exception, Double] = {
    env.get(name).map(_.eval(env)) match {
      case Some(value) => value
      case _ => Left(new ExpressionException(s"$name not found"))
    }
  }
}
