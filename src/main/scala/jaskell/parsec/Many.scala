package jaskell.parsec

import scala.collection.mutable
import scala.util.{Failure, Success, Try}

/**
 * Many try to parse the parse more times, and collect all results into a Seq.
 * Many could success 0 to any times.
 *
 * @author mars
 */
class Many[E, T](val parsec: Parsec[E, T]) extends Parsec[E, Seq[T]] {
  val psc = new Attempt[E, T](parsec)

  override def apply(s: State[E]): Try[Seq[T]] = {
    val re = new mutable.ListBuffer[T]
    while (true) {
      psc ask s match {
        case Success(result) => re += result
        case Failure(_) =>
          return Success(re.toSeq)
      }
    }
    s.trap("many parsec should arrived this for never")
  }
}

object Many {
  def apply[E, T](parsec: Parsec[E, T]): Many[E, T] = new Many[E, T](parsec)
}
