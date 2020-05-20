package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/20 11:27
 */
trait CouldOn extends Directive {
  def on(cond: Condition): On with CouldWhere = new On with CouldWhere {
    override val prefix: Directive = CouldOn.this
    override val condition: Condition = cond
  }
}
