package jaskell.parsec

import java.io.EOFException

import scala.collection.mutable
import scala.util.control.Breaks._

/**
 * sepEndBy1 p sep parses one or more occurrences of p, separated and optionally ended by sep.
 * Returns a list of values returned by p.
 *
 * @author mars
 * @version 1.0.0
 */
class SepEndBy1[T, E](val parser: Parsec[T, E], val sep: Parsec[_, E]) extends Parsec[Seq[T], E] {
  val separator = new Try(sep)
  val p = new Try(parser)

  val parsec: Parsec[Seq[T], E] = new Parsec[Seq[T], E] {
    override def ask(s: State[E]): Either[Exception, Seq[T]] = {
      val re = new mutable.ListBuffer[T]
      parser ? s map { head =>
        re += head
        while(true) {
          val tran = s.begin()
          (for {
            _ <- sep ? s
            r <- parser ? s
          } yield r) match {
            case Right(value) =>
              re += value
              s.commit(tran)
            case Left(_) =>
              s.rollback(tran)
              return Right(re.toSeq)
          }
        }
      }
      Right(re.toSeq)
    }
  }

  override def ask(s: State[E]): Either[Exception, Seq[T]] = parsec ? s
}
