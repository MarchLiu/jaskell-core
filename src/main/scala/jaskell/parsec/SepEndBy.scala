package jaskell.parsec

import scala.collection.mutable
import util.control.Breaks._

/**
 * SepEndBy p sep parses zero or more occurrences of p, separated and optionally ended by sep, ie.
 * haskell style statements. Returns a seq of values returned by p.
 *
 * @author mars
 * @version 1.0.0
 */
class SepEndBy[T, E](val parser: Parsec[T, E], val sep: Parsec[_, E]) extends Parsec[Seq[T], E] {
  val separator = new Try(sep)
  val p = new Try(parser)
  val parsec: Parsec[Seq[T], E] = (s: State[E]) => {
    val re = new mutable.ListBuffer[T]
    breakable {
      while (true) {
        val result = for {
          item <- p ask s
          _ <- separator ask s
        } yield item
        result match {
          case Right(v) => re += v
          case Left(_) => break()
        }
      }
    }
    Right(re.toSeq)
  }

  override def ask(s: State[E]): Either[Exception, Seq[T]] = parsec ? s
}
