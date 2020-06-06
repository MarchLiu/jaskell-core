package jaskell.parsec

import java.io.EOFException

import scala.collection.mutable
import scala.util.control.Breaks._

/**
 * Many try to parse the parse more times, and collect all results into a Seq.
 * Many must success once at lest
 *
 * @author Mars Liu
 * @version 1.0.0
 */
class Many1[T, E](val parsec: Parsec[T, E]) extends Parsec[Seq[T], E] {
  val psc = new Try[T, E](parsec)

  override def ask(s: State[E]): Either[Exception, Seq[T]] = {
    val re = new mutable.ListBuffer[T]
    parsec ask s match {
      case Right(value) => re += value
      case left: Left[Exception, Seq[T]] => return left
    }

    breakable {
      while (true) {
        psc ask s match {
          case Right(value) =>
            re += value
          case Left(_) =>
            break
        }
      }
    }
    Right(re.toSeq)
  }
}

object Many1 {
  def apply[T, E](parsec: Parsec[T, E]): Many1[T, E] = new Many1[T, E](parsec)
}
