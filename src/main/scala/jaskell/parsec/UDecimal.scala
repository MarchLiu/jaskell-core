package jaskell.parsec

import scala.collection.mutable

/**
 * UDecimal parser parse a decimal number without signed
 *
 * @author mars
 * @version 1.0.0
 */
class UDecimal extends Parsec[String, Char] {
  val uint = new jaskell.parsec.UInt()
  val dot: Try[Char, Char] = Try(Ch('.'))

  override def ask(st: State[Char]): Either[Exception, String] = {
    uint ask st flatMap { value =>
      (for {
        _ <- dot ? st
        tail <- uint ? st
      } yield {
        value + "." + tail
      }) orElse Right(value)
    }
  }
}

object UDecimal {
  def apply(): UDecimal = new UDecimal()
}
