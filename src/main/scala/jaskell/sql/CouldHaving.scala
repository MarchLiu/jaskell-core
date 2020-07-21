package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/07/10 16:17
 */
trait CouldHaving extends Directive {
  def having(c: Condition): Having = new Having {
    override val prefix: Directive = CouldHaving.this
    override val condition: Directive = c
  }
}
