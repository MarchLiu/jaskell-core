package jaskell.parsec

import java.io.EOFException

import scala.collection.mutable
import scala.util.{Failure, Success, Try}

/**
 * manyTill p end applies parser p zero or more times until parser end succeeds. Returns the list of
 * values returned by p. This parser can be used to scan comments.
 *
 * @author Mars Liu
 * @version 1.0.0
 */
class ManyTill[E, T, L](val parser: Parsec[E, T], val end: Parsec[E, L]) extends Parsec[E, Seq[T]] {
  val psc = new Attempt[E, T](parser)
  val till = new Attempt[E, L](end)



  override def ask(s: State[E]): Try[Seq[T]] = {
    val re = new mutable.ListBuffer[T]
    while (true)  {
      till ask s match {
        case Success(_) => return Success(re.toSeq)
        case Failure(error: EOFException) => return Failure(error)
        case Failure(_: ParsecException) =>
          parser ask s match {
            case Success(value) => re += value
            case Failure(e) => Failure(e)
          }
        case Failure(e) => Failure(e)
      }
    }
    Success(re.toSeq)
  }
}

object ManyTill {
  def apply[E, T, L](parser: Parsec[E, T], end: Parsec[E, L]): ManyTill[E, T, L] = new ManyTill(parser, end)
}