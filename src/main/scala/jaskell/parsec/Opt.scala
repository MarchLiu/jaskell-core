package jaskell.parsec

/**
 * Opt x p tries to apply parser p. If p fails without consuming input,
 * it returns the value x, otherwise the value returned by p.
 *
 * @author Mars Liu
 * @version 1.0.0
 * @since 2020/05/25 21:55
 */
class Opt[T, E](val p: Parsec[T, E], val otherwise: T) extends Parsec [T, E]{
  override def apply[S <: State[E]](s: S): T = {
    val before = s.status
    try {
      p(s)
    } catch {
      case error: Exception =>
        val after = s.status
        if(before == after) {
          otherwise
        }else{
          throw error
        }
    }
  }
}

object Opt{
  def apply[T, E](p: Parsec[T, E], otherwise: T): Opt[T, E] = new Opt(p, otherwise)
}
