package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/19 18:30
 */
trait CouldIn extends Directive {
  def in (q: Quote): In = new In {
    override val prefix: Directive = CouldIn.this
    override val quote: Quote = q
  }
}
