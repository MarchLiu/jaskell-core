package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/20 18:09
 */
trait CouldReturning extends Directive {
  def returning: Returning = new Returning {
    override val prefix: Directive = CouldReturning.this
  }
}
