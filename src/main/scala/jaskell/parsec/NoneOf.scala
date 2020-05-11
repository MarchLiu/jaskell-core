package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 14:00
 */
class NoneOf[E](val items: Set[E]) extends Parsec[E, E] {
  @throws[EofException]
  @throws[ParsecException]
  override def apply[S <: State[E]](s: S): E = {
    val re = s.next()
    if (items.contains(re)) {
      throw new ParsecException(s.status(), s"expect a item none of $items but got $re")
    }
    re
  }
}

object NoneOf {
  def apply[E](items: Set[E]): NoneOf[E] = new NoneOf(items)
}
