package jaskell.parsec

import scala.collection.mutable
import util.control.Breaks._

/**
 * SepEndBy p sep parses zero or more occurrences of p, separated and optionally ended by sep, ie.
 * haskell style statements. Returns a seq of values returned by p.
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/25 20:54
 */
class SepEndBy[T, E](val parser: Parsec[T, E], val sep: Parsec[_, E]) extends Parsec[Seq[T], E] {
  val separator = new Try(sep)
  val p = new Try(parser)
  val parsec: Parsec[Seq[T], E] = new Parsec[Seq[T], E] {
    override def apply[S <: State[E]](s: S): Seq[T] = {
      val re = new mutable.ListBuffer[T]
      breakable {
        while (true) {
          val result = for {
            item <- p either s
            _ <- separator either s
          } yield item
          result match {
            case Right(v) => re += v
            case Left(_) => break()
          }
        }
      }
      re.toSeq
    }
  }

  override def apply[S <: State[E]](s: S): Seq[T] = parsec(s)
}
