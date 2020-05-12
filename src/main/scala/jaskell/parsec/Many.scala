package jaskell.parsec

import java.io.EOFException

import scala.collection.mutable

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 14:55
 */
class Many[T, E](val parsec: Parsec[T, E]) extends Parsec[List[T], E] {
  val psc = new Try[T, E](parsec)

  override def apply[S <: State[E]](s: S): List[T] =  {
    var re = new mutable.MutableList[T]
    try {
      while (true) {
        re += psc(s)
      }
    } catch {
      case e@(_: EOFException| _: ParsecException) =>
        re.toList
    }
    re.toList
  }
}

object Many {
  def apply[T, E](parsec: Parsec[T, E]): Many[T, E] = new Many[T, E](parsec)
}
