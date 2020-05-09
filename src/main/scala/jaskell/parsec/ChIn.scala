package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 13:51
 */
class ChIn(val content: String) extends OneOf[Char](content.toSet) with Parsec[Char, Char] {

}
