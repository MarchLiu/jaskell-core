package jaskell.parsec

import java.io.EOFException

import scala.collection.mutable

/**
 * sepBy p sep parses zero or more occurrences of p, separated by sep. Returns a list of values returned by p.
 *
 * @author Mars Liu
 * @version 1.0.0
 */
class SepBy[T, E](val parsec: Parsec[T, E], val by: Parsec[_, E]) extends Parsec[Seq[T], E] {
  val b: Try[_, E] = Try(by)
  val p: Try[T, E] = Try[T, E](parsec)
  val psc: Parsec[T, E] = b >> p

  override def ask(s: State[E]): Either[Exception, Seq[T]] = {
    val re = new mutable.ListBuffer[T]
    p ? s map { head =>
      re += head
      while (true) {
        psc ? s match {
          case Right(r) => re += r
          case Left(_) => return Right(re.toSeq)
        }
      }
      re.toSeq
    }
  }
}

object SepBy {
  def apply[T, E](parsec: Parsec[T, E], by: Parsec[_, E]): SepBy[T, E] = new SepBy(parsec, by)
}
