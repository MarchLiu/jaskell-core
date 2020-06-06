package jaskell.parsec

import java.io.EOFException

import scala.collection.mutable

/**
 * manyTill p end applies parser p zero or more times until parser end succeeds. Returns the list of
 * values returned by p. This parser can be used to scan comments.
 *
 * @author Mars Liu
 * @version 1.0.0
 */
class ManyTill[T, L, E](val parser: Parsec[T, E], val end: Parsec[L, E]) extends Parsec[Seq[T], E] {
  val psc = new Try[T, E](parser)
  val till = new Try[L, E](end)



  override def ask(s: State[E]): Either[Exception, Seq[T]] = {
    val re = new mutable.ListBuffer[T]
    while (true)  {
      till ask s match {
        case Right(_) => return Right(re.toSeq)
        case Left(error: EOFException) => return Left(error)
        case Left(_: ParsecException) =>
          parser ask s match {
            case Right(value) => re += value
            case left: Left[Exception, Seq[T]] => return left
          }
      }
    }
    Right(re.toSeq)
  }
}

object ManyTill {
  def apply[T, L,  E](parser: Parsec[T, E], end: Parsec[L, E]): ManyTill[T, L, E] = new ManyTill(parser, end)
}