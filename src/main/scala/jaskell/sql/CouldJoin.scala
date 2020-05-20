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
    override val opt = ""
    override val prefix: Directive = CouldJoin.this
    override val j: CouldBeJoin = jo
  }

  def leftJoin(jo: CouldBeJoin): Join = new Join {
    override val opt: String = "LEFT"
    override val prefix: Directive = CouldJoin.this
    override val j: CouldBeJoin = jo
  }

  def rightJoin(jo: CouldBeJoin): Join = new Join {
    override val opt: String = "RIGHT"
    override val prefix: Directive = CouldJoin.this
    override val j: CouldBeJoin = jo
  }
}
