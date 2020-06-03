package jaskell.parsec

import java.io.EOFException

import scala.collection.mutable

/**
 * Many try to parse the parse more times, and collect all results into a Seq.
 * Many must success once at lest
 *
 * @author Mars Liu
 * @version 1.0.0
 * @since 2020/05/09 15:22
 */
class Many1[T, E](val parsec: Parsec[T, E]) extends Parsec[List[T], E] {
  val psc = new Try[T, E](parsec)

  override def apply[S <: State[E]](s: S): List[T] =  {
    var re = new mutable.ListBuffer[T]
    re += parsec(s)
    try {
      while (true) {
        re += psc(s)
      }
    } catch {
      case _@(_: ParsecException | _: EOFException) =>
        return re.toList
    }
    re.toList
  }
}

object Many1 {
  def apply[T, E](parsec: Parsec[T, E]): Many1[T, E] = new Many1[T, E](parsec)
}
