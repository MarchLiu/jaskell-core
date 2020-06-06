package jaskell.parsec

import scala.collection.mutable

/**
 * UDecimal parser parse a decimal number without signed
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 14:21
 */
class UDecimal extends Parsec[String, Char]{
  val uint = new jaskell.parsec.UInt()
  val dot: Try[Char, Char] = Try(Ch('.'))
  override def apply[S <: State[Char]](s: S): String = {
    val sb: mutable.StringBuilder = new mutable.StringBuilder();
    sb ++= uint(s)
    dot.ask(s) match {
      case Left(_) => sb.mkString
      case Right(_) =>
        sb += '.'
        sb ++= uint(s)
        sb.mkString
    }
  }
}

object UDecimal {
  def apply(): UDecimal = new UDecimal()
}
