package jaskell.parsec

import scala.collection.mutable

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 14:21
 */
class UDecimal extends Parsec[String, Char]{
  val uint = new jaskell.parsec.UInt()
  val dot: Ch = Ch('.')
  override def apply[S <: State[Char]](s: S): String = {
    val sb: mutable.StringBuilder = new mutable.StringBuilder();
    sb ++ uint(s)
    dot.either(s) match {
      case Left(_) => sb.mkString.asInstanceOf
      case Right(_) =>
        sb += '.'
        sb ++ uint(s)
        sb.mkString.asInstanceOf
    }
  }
}

object UDecimal {
  def apply(): UDecimal = new UDecimal()
}
