package jaskell.sql

import scala.collection.mutable

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 16:12
 */
trait CouldGroup extends Directive {
  def group: Group = new Group {
    override val prefix: Directive  = CouldGroup.this
  }
}
