package jaskell.parsec

import java.io.EOFException

import scala.collection.mutable

/**
 * Many try to parse the parse more times, and collect all results into a Seq.
 * Many could success 0 to any times.
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 14:55
 */
class Many[T, E](val parsec: Parsec[T, E]) extends Parsec[Seq[T], E] {
  val psc = new Try[T, E](parsec)

  override def apply[S <: State[E]](s: S): Seq[T] =  {
    var re = new mutable.ListBuffer[T]
    try {
      while (true) {
        re += psc(s)
      }
    } catch {
      case e@(_: EOFException| _: ParsecException) =>
        re.toSeq
    }
    re.toSeq
  }
}

object Many {
  def apply[T, E](parsec: Parsec[T, E]): Many[T, E] = new Many[T, E](parsec)
}
