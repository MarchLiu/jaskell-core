package jaskell.sql

import scala.collection.mutable

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 16:12
 */
trait CouldOrder extends Directive {
  def order:Order = new Order {
    override val prefix: Directive = CouldOrder.this
  }
}
