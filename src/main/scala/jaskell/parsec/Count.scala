package jaskell.parsec

import scala.collection.mutable
import scala.util.{Failure, Success, Try}

/**
 * count n p parses n occurrences of p. If n is smaller or equal to zero, the parser equals to return [].
 * Returns a list of n values returned by p.
 *
 * @author Mars Liu
 * @version 1.0.0
 */
class Count[E, T](val p: Parsec[E, T], val n: scala.Int) extends Parsec[E, Seq[T]] {

  override def ask(s: State[E]): Try[Seq[T]] = {
    if (n <= 0) {
      return Success(Seq.empty)
    }
    val re = mutable.ListBuffer[T]()
    for (_ <- 0 to n) {
      p ? s match {
        case Success(value) => re += value
        case Failure(error) =>
          return Failure(error)
      }
    }
    Success(re.toSeq)
  }
}

object Count {
  def apply[E, T](p: Parsec[E, T], n: scala.Int): Count[E, T] = new Count(p, n)
}