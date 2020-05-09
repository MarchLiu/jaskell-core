package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 16:47
 */
class NeCh[Status, Tran](val ch: Char) extends Ne[Char](element = ch) with Parsec[Char, Char] {
}
