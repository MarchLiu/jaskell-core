package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 */
trait State[+E] {
  type Status
  type Tran
  def status: Status

  def begin(): Tran

  def begin(tran: Tran): Tran

  def commit(tran: Tran): Unit

  def rollback(tran: Tran): Unit

  def next(): Either[Exception, E]

  def trap[T](message: String): Left[Exception, T] = {
    Left(new ParsecException(this.status, message))
  }
}

object State {
  def apply(str: String): TxtState = new TxtState(str)

  def apply[T](c: Seq[T]): CommonState[T] = new CommonState[T] {
    override val content: Seq[T] = c
  }
}

