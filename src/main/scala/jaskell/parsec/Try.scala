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
    try {
      val re = parsec(s)
      s.commit(tran)
      re
    } catch {
      e: Exception => {
        s.rollback(tran)
        throw e
      }
    }
  }
}
