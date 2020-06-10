package jaskell.expression

import scala.collection.mutable


/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/10 13:30
 */
class Env {
  val stack: mutable.HashMap[String, Expression] = mutable.HashMap()

  def put(name: String, value: Double): Option[Expression] = {
    stack.put(name, new N(value))
  }

  def put(name: String, value: Expression): Option[Expression] = {
    stack.put(name, value)
  }

  def get(name: String): Option[Expression] = {
    stack.get(name)
  }
}
