package jaskell.sql

import scala.collection.mutable

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 17:10
 */
trait Order {
  val prefix: Directive
  def by: By = {
    new By with CouldOrder {
      override val _prefix: Directive = prefix
      override val _operator: String = "ORDER"
      override val columns:  mutable.ListBuffer[_ <: Directive] = new mutable.ListBuffer[Directive]
    }
  }
}
