package jaskell.parsec

/**
 * Maybe p tries to apply parser p. If p fails without consuming input, it return Nothing,
 * otherwise it returns Just the value returned by p.
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/25 21:52
 */
class Maybe[T, E](val p: Parsec[T, E]) extends Parsec[Option[T], E] {
  override def apply[S <: State[E]](s: S): Option[T] = {
    val before = s.status
    try {
      Some(p(s))
    } catch {
      case error: Exception =>
        val after = s.status
        if (before == after) {
          None
        } else {
          throw error
        }
    }
  }
}
