package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 13:59
 */
class ChNone(val content: String) extends NoneOf[Char](content.toSet) with Parsec[Char, Char] {

}
