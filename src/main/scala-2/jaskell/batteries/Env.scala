package jaskell.batteries

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2022/05/06 00:16
 */
trait Env {
  def find(name: String): String
}
