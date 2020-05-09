package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 16:53
 */
class Return[T, E](val element: T) extends Parsec[T, E] {
  override def apply[S <: State[E]](s: S): T = element
}
