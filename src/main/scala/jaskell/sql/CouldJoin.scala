package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 18:57
 */
trait CouldJoin extends Directive {
  def join(jo: CouldBeJoin): Join = new Join {
    override val j: CouldBeJoin = jo
    override val prefix: Directive = this
  }
}
