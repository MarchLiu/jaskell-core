package jaskell.parsec

import java.io.EOFException

import scala.collection.mutable

/**
 * SepBy1 p sep parses one or more occurrences of p, separated by sep. Returns a list of values returned by p.
 *
 * @author mars
 * @version 1.0.0
 */
class SepBy1[T, E](val parsec: Parsec[T, E], val by: Parsec[_, E]) extends Parsec[Seq[T], E] {
  val b = new Try(by)
  val p = new Try[T, E](parsec)

  override def ask(s: State[E]): Either[Exception, Seq[T]] = {
    val re = new mutable.ListBuffer[T]
    parsec ? s map { head =>
      re += head
      while (true) {
        (for {
          _ <- b ? s
          r <- p ? s
        } yield r) match {
          case Right(value) => re += value
          case Left(_) => return Right(re.toSeq)
        }
      }
      return Right(re.toSeq)
    }
  }
}

object SepBy1 {
  def apply[T, E](parsec: Parsec[T, E], by: Parsec[_, E]): SepBy1[T, E] = new SepBy1(parsec, by)
}