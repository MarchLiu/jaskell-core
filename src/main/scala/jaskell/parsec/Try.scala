package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 14:22
 */
class Try[T, E](val parsec: Parsec[T, E]) extends Parsec[T, E] {
  override def apply[S <: State[E]](s: S): T = {
    val tran = s.begin()
    parsec either s match {
      case Right(result) =>
        s commit tran
        result
      case Left(e) =>
        s rollback tran
        throw e
    }
  }
}

object Try {
  def apply[T, E](parsec: Parsec[T, E]): Try[T, E] = new Try(parsec)
}
