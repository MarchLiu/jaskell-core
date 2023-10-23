package jaskell.parsec

import jaskell.Monad.Implicits.toMonad
import jaskell.parsec.Parsec.Implicits.mkMonad

import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2023/10/22 00:43
 */
class Enumerate[E, T](val parsecs: Parsec[E, T]*)(val sep: Parsec[E, _] = Return[E, Unit](())) extends Parsec[E, Set[T]] {

  override def apply(s: State[E]): Try[Set[T]] = {
    if (parsecs.isEmpty) {
      s.trap("enumerate parsers must not empty")
    } else {
      headHelper(parsecs, s) flatMap { value =>
        val tail = parsecs.tail.map(p => sep *> p)
        Success(helper(tail, s) + value)
      }
    }
  }

  def by(sep: Parsec[E, _]): Enumerate[E, T] = new Enumerate[E, T](parsecs: _*)(sep)

  @tailrec
  private def headHelper(parsers: Seq[Parsec[E, T]], s: State[E]): Try[T] = {
    val head = parsers.head
    head(s) match {
      case Success(value) =>
        Success(value)
      case Failure(error) =>
        val tail = parsers.tail
        if (tail.isEmpty) {
          Failure(error)
        } else {
          headHelper(tail, s)
        }
    }
  }

  @tailrec
  private def helper(parsers: Seq[Parsec[E, T]], s: State[E], acc: Set[T] = Set()): Set[T] = {
    if (parsers.isEmpty) {
      acc
    } else {
      val head = parsers.head
      head(s) match {
        case Success(value) =>
          helper(parsers.tail, s, acc + value)
        case Failure(_) =>
          helper(parsers.tail, s, acc)
      }
    }
  }
}
