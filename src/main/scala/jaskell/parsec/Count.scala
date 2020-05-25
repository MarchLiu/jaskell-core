package jaskell.parsec

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
 * count n p parses n occurrences of p. If n is smaller or equal to zero, the parser equals to return [].
 * Returns a list of n values returned by p.
 *
 * @author Mars Liu
 * @version 1.0.0
 * @since 2020/05/25 23:02
 */
class Count[T, E](val p: Parsec[T, E], val n:scala.Int) extends Parsec [Seq[T], E]{
  override def apply[S <: State[E]](s: S): Seq[T] = {
    if(n <= 0){
      return Seq.empty
    }
    val re = mutable.ListBuffer[T]()
    for(_ <- 0 to n){
      re += p(s)
    }
    re.toSeq
  }
}

object Count{
  def apply[T, E](p: Parsec[T, E], n: scala.Int): Count[T, E] = new Count(p, n)
}