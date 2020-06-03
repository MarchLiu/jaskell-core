package jaskell.expression

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/02 21:36
 */
class Product(l: Expression, r: Expression) extends Binary(l, r) {
  override def eval: Double = left.eval * right.eval

  override def priority: Int = 2
}
