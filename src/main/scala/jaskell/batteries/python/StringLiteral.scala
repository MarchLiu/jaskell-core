package jaskell.batteries.python

import jaskell.batteries.Token

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2022/05/06 00:10
 */
trait StringLiteral extends Token{
  val prefix: String
  val literal: String
  val left: String
  val right: String
}
