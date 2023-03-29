package jaskell.croupier

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2023/03/28 19:01
 */
trait Scale[T] {
  def weight(item: T): Int
}
