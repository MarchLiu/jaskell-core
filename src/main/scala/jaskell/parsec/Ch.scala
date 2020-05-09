package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 13:50
 */
class Ch(val chr: Char) extends Eq[Char](chr) with Parsec[Char, Char] {
}