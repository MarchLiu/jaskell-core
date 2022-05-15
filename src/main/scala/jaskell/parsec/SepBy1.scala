package jaskell.parsec

import scala.collection.mutable
import scala.util.{Failure, Success, Try}

/**
 * SepBy1 p sep parses one or more occurrences of p, separated by sep. Returns a list of values returned by p.
 *
 * @author mars
 * @version 1.0.0
 */
class SepBy1[E, T](val parsec: Parsec[E, T], val by: Parsec[E, _]) extends Parsec[E, Seq[T]] {
  val b = new Attempt(by)
  val p = new Attempt[E, T](parsec)
  val psc: Parsec[E, T] = (s: State[E]) => for {
    _ <- b ask s
    re <- p ask s
  } yield re

  override def apply(s: State[E]): Try[Seq[T]] = {
    val re = new mutable.ListBuffer[T]
    parsec ? s map { head =>
      re += head
      while (true) {
        psc ? s match {
          case Success(value) => re += value
          case Failure(_) => return Success(re.toSeq)
        }
      }
      return Success(re.toSeq)
    }
  }
}

object SepBy1 {
  def apply[E, T](parsec: Parsec[E, T], by: Parsec[E, _]): SepBy1[E, T] = new SepBy1(parsec, by)
}