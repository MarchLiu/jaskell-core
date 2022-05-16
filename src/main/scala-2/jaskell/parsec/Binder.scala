package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/12 19:03
 */
trait Binder[E, T, O] {
  def apply(value: T): Parsec[E, O]
}
