package jaskell.parsec

import scala.collection.mutable
import scala.util.{Failure, Success, Try}
import scala.util.control.Breaks._

/**
 * Many try to parse the parse more times, and collect all results into a Seq.
 * Many must success once at lest
 *
 * @author Mars Liu
 * @version 1.0.0
 */
class Many1[E, T](val parsec: Parsec[E, T]) extends Parsec[E, Seq[T]] {
  val psc = new Attempt[E, T](parsec)

  override def ask(s: State[E]): Try[Seq[T]] = {
    val re = new mutable.ListBuffer[T]
    parsec ask s match {
      case Success(value) => re += value
      case Failure(e) => return Failure(e)
    }

    breakable {
      while (true) {
        psc ask s match {
          case Success(value) =>
            re += value
          case Failure(_) =>
            break
        }
      }
    }
    Success(re.toSeq)
  }
}

object Many1 {
  def apply[E, T](parsec: Parsec[E, T]): Many1[E, T] = new Many1[E, T](parsec)
}
