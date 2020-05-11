package jaskell.parsec

import scala.collection.mutable

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 16:39
 */
class ManyTill[T, L, E](val parser: Parsec[T, E], val end: Parsec[L, E]) extends Parsec[List[T], E] {
  val psc = new Try[T, E](parser)
  val till = new Try[L, E](end)

  @throws[EofException]
  @throws[ParsecException]
  override def apply[S <: State[E]](s: S): List[T] = {
    val re = new mutable.MutableList[T]
    while (true) try {
      till(s)
      return re.toList
    } catch {
      case error: EofException =>
        throw error
      case _: ParsecException =>
        re += parser(s)
    }
    re.toList
  }
}

object ManyTill {
  def apply[T, L,  E](parser: Parsec[T, E], end: Parsec[L, E]): ManyTill[T, L, E] = new ManyTill(parser, end)
}