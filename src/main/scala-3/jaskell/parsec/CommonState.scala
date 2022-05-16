package jaskell.parsec

import scala.util.{Try, Success, Failure}
import java.io.EOFException

/**
 * Common State has int status and transaction market. It can apply to Seq[T] or any serial collection.
 *
 * @author Mars Liu
 * @version 1.0.0
 */
class CommonState[T](val content: Seq[T]) extends State[T]:
  override type Status = scala.Int
  override type Tran = scala.Int
  
  var current: scala.Int = 0
  var tran: scala.Int = -1

  def next(): Try[T] = 
    if (content.size <= current) {
      return Failure(new EOFException())
    } else {
      val re = content(current)
      current += 1
      return Success(re)
    }

  def status: Status = current

  def begin(): Tran =
    if (this.tran == -1) this.tran = this.current
    current

  def begin(tran: Tran): Tran =
    if (this.tran > tran) this.tran = tran
    this.tran

  def rollback(tran: Tran): Unit =
    if (this.tran == tran) this.tran = -1
    this.current = tran

  def commit(tran: Tran): Unit = if (this.tran == tran) this.tran = -1
