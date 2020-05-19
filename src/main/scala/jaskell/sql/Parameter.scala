package jaskell.sql

import java.util
import java.util.{ArrayList, List, Optional}


class Parameter[T] protected() extends Directive {
  var value: Option[T] = None
  var key: Any = _
  var _order: Int = _
  var placeHolder: String = _

  def set(v: T): Unit = {
    value = Option[T](v)
  }

  def confirmed: Boolean = value != null


  def order: Int = _order

  def order(o: Int): Unit = {
    this._order = o
  }

  override def script: String = placeHolder

  override def parameters: Seq[Parameter[T]] = {
    Seq[Parameter[T]](this)
  }
}

object Parameter {
  def apply[T](ph: String, k: Any): Parameter[T] = new Parameter[T]() {
    placeHolder = ph
    key = k
    value = None
  }

  def apply[T](ph: String, k: Any, v:T): Parameter[T] = new Parameter[T]() {
    placeHolder = ph
    key = k
    value = Some(v)
  }
}