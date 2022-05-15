package jaskell.parsec

import scala.util.{Failure, Success, Try}

/**
 * UDecimal parser parse a decimal number without signed
 *
 * @author mars
 * @version 1.0.0
 */
class UDecimal extends Parsec[Char, String] {
  val uint = new jaskell.parsec.UInt()
  val dot: Attempt[Char, Char] = Attempt(Ch('.'))
  val tail: Parsec[Char, String] = (s: State[Char]) => for {
    _ <- dot ask s
    re <- uint ask s
  } yield re

  override def apply(st: State[Char]): Try[String] = {
    uint ask st map { value =>
      tail ? st match {
        case Failure(_) => value
        case Success(t) => s"$value.$t"
      }
    }
  }
}

object UDecimal {
  def apply(): UDecimal = new UDecimal()
}
