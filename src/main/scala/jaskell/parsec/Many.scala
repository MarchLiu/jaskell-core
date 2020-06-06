package jaskell.parsec

import java.io.EOFException

import scala.collection.mutable

/**
 * Many try to parse the parse more times, and collect all results into a Seq.
 * Many could success 0 to any times.
 *
 * @author mars
 */
class Many[T, E](val parsec: Parsec[T, E]) extends Parsec[Seq[T], E] {
  val psc = new Try[T, E](parsec)

  override def ask(s: State[E]): Either[Exception, Seq[T]] = {
    var re = new mutable.ListBuffer[T]
    while (true) {
      psc ask s match {
        case Right(result) => re += result
        case Left(_) =>
          return Right(re.toSeq)
      }
    }
    Left(new ParsecException(s.status, "many parsec should arrived this for never"))
  }
}

object Many {
  def apply[T, E](parsec: Parsec[T, E]): Many[T, E] = new Many[T, E](parsec)
}
