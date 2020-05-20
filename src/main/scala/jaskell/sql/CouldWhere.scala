package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 17:26
 */
trait CouldWhere extends Directive {
  def where(c: Condition): Where = new Where {
    override val prefix: Directive = CouldWhere.this
    override val condition: Directive = c
  }
}
