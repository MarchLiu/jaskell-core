package jaskell.parsec

import scala.collection.mutable

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 16:55
 */
class SepBy[T, E](val parsec: Parsec[T, E], val by: Parsec[_, E]) extends Parsec[List[T], E] {
  val b = new Try(by)
  val p = new Try[T, E](parsec)

  @throws[EofException]
  @throws[ParsecException]
  override def apply[S <: State[E]](s: S): List[T] = {
    val re = new mutable.MutableList[T]
    try {
      re += p(s)
      while (true) {
        b(s)
        re += p(s)
      }
      re.toList
    } catch {
      case _@(_: EofException | _: ParsecException) =>
        re.toList
    }
  }
}
