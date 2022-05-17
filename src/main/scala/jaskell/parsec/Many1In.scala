package jaskell.parsec

import scala.util.Try

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2022/05/06 00:47
 */
case class Many1In[E](toggle: Set[E]) extends Parsec[E, Seq[E]]{
  val parser: Parsec[E, Seq[E]] = Many1(OneOf(toggle))
  override def apply(s: State[E]): Try[Seq[E]] = parser(s)
}
