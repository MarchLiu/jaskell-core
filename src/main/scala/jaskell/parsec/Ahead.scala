package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 13:33
 */
class Ahead[T, E](var parser: Parsec[T, E]) extends Parsec[T, E] {

  @throws[EofException]
  @throws[ParsecException]
  override def apply[S <: State[E]](s: S): T = {
    val tran = s.begin()
    try parser(s)
    finally s.rollback(tran)
  }
}

object Ahead {
  def apply[T, E](parser: Parsec[T, E]): Ahead[T, E] = new Ahead[T, E](parser)
}
