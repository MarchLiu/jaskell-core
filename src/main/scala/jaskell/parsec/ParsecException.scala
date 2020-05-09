package jaskell.parsec

import scala.math.Ordering.String

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/08 22:29
 */
class ParsecException(val status: Any, val message: String) extends Exception {
}
