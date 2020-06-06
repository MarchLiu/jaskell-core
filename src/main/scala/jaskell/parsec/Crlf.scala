package jaskell.parsec

import java.io.EOFException

/**
 * Crlf 即 haskell parsec 的 crlf 算子,匹配 \r\n .
 *
 * @author mars
 * @version 1.0.0
 */
class Crlf(val r:Ch = Ch('\r'), val n:Ch = Ch('\n')) extends Parsec[String, Char] {

  override def ask(s: State[Char]): Either[Exception, String] = {
    for{
      _ <- r ? s
      _ <- n ? s
    } yield {"\r\n"}
  }
}

object Crlf {
  def apply(): Crlf = new Crlf()
}
