package jaskell.sql

import scala.collection.mutable

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 16:52
 */
trait By extends Directive {
  val _prefix: Directive
  val _operator: String

  val columns: mutable.MutableList[_ <: Directive]
  override def script: String =  {
    _prefix.script + " " + _operator + " BY " + columns.map(_.script).mkString(", ")
  }

  override val parameters: Seq[Parameter[_]] = _prefix.parameters ++ columns.flatMap(_.parameters)
}
