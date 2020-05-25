package jaskell.parsec

import java.io.EOFException

/**
 * Common State has int status and transaction market. It can apply to Seq[T] or any serial collection.
 *
 * @author Mars Liu
 * @version 1.0.0
 * @since 2020/05/08 14:47
 */
trait CommonState[T] extends State[T] {
  override type Status = scala.Int
  override type Tran = scala.Int

  val content: Seq[T]
  var current: scala.Int = 0
  var tran: scala.Int = -1

  @throws[EOFException]
  def next(): T = {
    if (content.size <= current) {
      throw new EOFException();
    } else {
      val re = content(current)
      current += 1
      re
    }
  }

  def status: scala.Int = {
    current
  }

  def begin(): scala.Int = {
    if (this.tran == -1) this.tran = this.current
    current
  }

  def begin(tran: scala.Int): scala.Int = {
    if (this.tran > tran) this.tran = tran
    this.tran
  }

  def rollback(tran: scala.Int): Unit = {
    if (this.tran == tran) this.tran = -1
    this.current = tran
  }

  def commit(tran: scala.Int): Unit = {
    if (this.tran == tran) this.tran = -1
  }
}



