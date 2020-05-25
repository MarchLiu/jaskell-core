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
 * @since 2020/05/25 20:54
 */
class SepEndBy1[T, E](val parser: Parsec[T, E], val sep: Parsec[_, E]) extends Parsec[Seq[T], E] {
  val separator = new Try(sep)
  val p = new Try(parser)
  val parsec: Parsec[Seq[T], E] = new Parsec[Seq[T], E] {
    override def apply[S <: State[E]](s: S): Seq[T] = {
      val re = new mutable.ListBuffer[T]
      re += parser(s)
      breakable {
        while (true) {
          val tran = s.begin()
          try {
            sep(s)
            re += parser(s)
            s.commit(tran)
          } catch {
            case _: Exception =>
              s.rollback(tran)
              break()
          }
        }
      }
      re.toSeq
    }
  }

  override def apply[S <: State[E]](s: S): Seq[T] = parsec(s)
}
