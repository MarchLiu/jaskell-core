package jaskell.parsec

import jaskell.parsec.Parsec.Implicits._
import scala.collection.mutable
import scala.util.{Failure, Success, Try}

/**
 * sepBy p sep parses zero or more occurrences of p, separated by sep. Returns a list of values returned by p.
 *
 * @author Mars Liu
 * @version 1.0.0
 */
class SepBy[E, T](val parsec: Parsec[E, T], val by: Parsec[E, _]) extends Parsec[E, Seq[T]] {
  val b: Attempt[E, _] = Attempt(by)
  val p: Attempt[E, T] = Attempt[E, T](parsec)
  val psc: Parsec[E, T] = (s: State[E]) => for {
    _ <- b ask s
    re <- p ask s
  } yield re

  override def apply(s: State[E]): Try[Seq[T]] = {
    val re = new mutable.ListBuffer[T]
    p ? s map { head =>
      re += head
      while (true) {
        psc ? s match {
          case Success(r) => re += r
          case Failure(_) => return Success(re.toSeq)
        }
      }
      re.toSeq
    }
  }
}

object SepBy {
  def apply[E, T](parsec: Parsec[E, T], by: Parsec[E, _]): SepBy[E, T] = new SepBy(parsec, by)
}
