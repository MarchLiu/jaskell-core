package jaskell.parsec

import scala.collection.mutable
import scala.util.{Failure, Success, Try}
import util.control.Breaks._

/**
 * SepEndBy p sep parses zero or more occurrences of p, separated and optionally ended by sep, ie.
 * haskell style statements. Returns a seq of values returned by p.
 *
 * @author mars
 * @version 1.0.0
 */
class SepEndBy[E, T](val parser: Parsec[E, T], val sep: Parsec[E, _]) extends Parsec[E, Seq[T]] {
  val separator = new Attempt(sep)
  val p = new Attempt(parser)
  val parsec: Parsec[E, Seq[T]] = (s: State[E]) => {
    val re = new mutable.ListBuffer[T]
    breakable {
      while (true) {
        if ((separator ? s).isFailure) {
          break
        }
        val tran = s.begin()
        p ? s match {
          case Success(value) =>
            re += value
            s commit tran
          case Failure(_) =>
            s rollback tran
            break
        }
      }
    }
    Success(re.toSeq)
  }

  override def ask(s: State[E]): Try[Seq[T]] = parsec ? s
}
