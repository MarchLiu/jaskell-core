package jaskell.parsec

import java.io.EOFException

import scala.collection.mutable

/**
 * SepBy1 p sep parses one or more occurrences of p, separated by sep. Returns a list of values returned by p.
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 16:55
 */
class SepBy1[T, E](val parsec: Parsec[T, E], val by: Parsec[_, E]) extends Parsec[List[T], E] {
  val b = new Try(by)
  val p = new Try[T, E](parsec)

  @throws[EOFException]
  @throws[ParsecException]
  override def apply[S <: State[E]](s: S): List[T] = {
    val re = new mutable.ListBuffer[T]
    re += parsec(s)
    try {
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

object SepBy1 {
  def apply[T, E](parsec: Parsec[T, E], by: Parsec[_, E]): SepBy1[T, E] = new SepBy1(parsec, by)
}