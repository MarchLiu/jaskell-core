package jaskell.parsec

/**
 * Parser throw ParsecException if failed.
 *
 * @author Mars Liu
 * @version 1.0.0
 */
class ParsecException(val status: Any, val message: String) extends Exception {
  override def getMessage: String = message
}
