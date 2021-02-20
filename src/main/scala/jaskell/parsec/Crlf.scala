package jaskell.parsec

import scala.util.Try

/**
 * Crlf 即 haskell parsec 的 crlf 算子,匹配 \r\n .
 *
 * @author mars
 * @version 1.0.0
 */
class Crlf(val r: Ch = Ch('\r'), val n: Ch = Ch('\n')) extends Parsec[Char, String] {

  override def apply(s: State[Char]): Try[String] = {
    for {
      _ <- r ? s
      _ <- n ? s
    } yield "\r\n"
  }
}

object Crlf {
  def apply(): Crlf = new Crlf()
}
