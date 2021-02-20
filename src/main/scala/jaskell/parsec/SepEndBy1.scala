package jaskell.parsec

import scala.collection.mutable
import scala.util.{Failure, Success, Try}
import scala.util.control.Breaks._
import jaskell.Monad.toMonad

/**
 * SepEndBy1 p sep parses one or more occurrences of p, separated and optionally ended by sep.
 * Returns a list of values returned by p.
 *
 * @author mars
 * @version 1.0.0
 */
class SepEndBy1[E, T](val parser: Parsec[E, T], val sep: Parsec[E, _]) extends Parsec[E, Seq[T]] {
  val separator = new Attempt(sep)
  val p: Parsec[E, T] = Attempt(parser)
  val psc: Parsec[E, T] = s => for {
    _ <- sep ask s
    re <- p ask s
  } yield re

  val parsec: Parsec[E, Seq[T]] = (s: State[E]) => {
    val re = new mutable.ListBuffer[T]

    parser ? s map { head =>
      re += head
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
    }
    Success(re.toSeq)
  }

  override def apply(s: State[E]): Try[Seq[T]] = parsec ? s
}
