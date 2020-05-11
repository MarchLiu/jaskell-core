package jaskell.parsec

import scala.collection.mutable

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 15:22
 */
class Many1[T, E](val parsec: Parsec[T, E]) extends Parsec[List[T], E] {
  val psc = new Try[T, E](parsec)

  override def apply[S <: State[E]](s: S): List[T] =  {
    var re = new mutable.MutableList[T]
    re += psc(s)
    try {
      while (true) {
        re += psc(s)
      }
    } catch {
      case err: EofException =>
        throw err
      case _: ParsecException =>
        re.toList
    }
    re.toList
  }
}

object Many1 {
  def apply[T, E](parsec: Parsec[T, E]): Many1[T, E] = new Many1[T, E](parsec)
}
