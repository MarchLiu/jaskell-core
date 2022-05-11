package jaskell.batteries

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2022/05/06 00:15
 */
trait Token {
  def parse(env: Env): String
}
