package jaskell.parsec

import java.io.EOFException

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

  @throws[EOFException]
  @throws[ParsecException]
  override def apply[S <: State[E]](s: S): List[T] = {
    val re = new mutable.ListBuffer[T]
    try {
      re += p(s)
      while (true) {
        b(s)
        re += p(s)
      }
      re.toList
    } catch {
      case _@(_: EOFException | _: ParsecException) =>
        re.toList
    }
  }
}

object SepBy {
  def apply[T, E](parsec: Parsec[T, E], by: Parsec[_, E]): SepBy[T, E] = new SepBy(parsec, by)
}
