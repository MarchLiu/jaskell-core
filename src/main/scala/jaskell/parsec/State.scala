package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/08 17:06
 */
trait State[T] {
  type Status
  type Tran
  def status(): Status

  def begin(): Tran

  def begin(tran: Tran): Tran

  def commit(tran: Tran): Unit

  def rollback(tran: Tran): Unit

  def next(): T
}

object State {
  def apply(c: String): CommonState[Char] = new CommonState[Char] {
    override val content: Seq[Char] = c.seq
  }

  def apply[T](c: Seq[T]): CommonState[T] = new CommonState[T] {
    override val content: Seq[T] = c
  }
}

