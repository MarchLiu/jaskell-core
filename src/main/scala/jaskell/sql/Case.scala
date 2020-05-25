package jaskell.sql

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 19:36
 */
class Case(val base: Expression = new Empty with Expression) extends Directive {
  val whens: mutable.ListBuffer[(Condition, Expression)] = new ListBuffer
  def when(condition: Condition, `then`: Expression): this.type = {
    whens += Tuple2(condition, `then`)
    this
  }

  def end: Directive with CouldBeColumn with Expression with CouldAs =
    new Directive with CouldBeColumn with Expression with CouldAs {
      override def script: String = {
        val dispatches = whens.map({pair =>
          val (c, t) = pair
          s"WHEN ${c.script} THEN ${t.script}"
        }).mkString
        s"CASE ${base.script} $dispatches END"
      }

      override def parameters: Seq[Parameter[_]] = base.parameters ++ (whens flatMap {pair =>
        val (c, t) = pair
        c.parameters ++ t.parameters
      })
    }

  override def script: String = {
    val dispatches = whens.map({pair =>
      val (c, t) = pair
      s"WHEN ${c.script} THEN ${t.script}"
    }).mkString
    s"CASE ${base.script} $dispatches END"
  }

  override def parameters: Seq[Parameter[_]] = base.parameters ++ (whens flatMap {pair =>
    val (c, t) = pair
    c.parameters ++ t.parameters
  })
}

object Case {
  def apply(base: Expression): Case = new Case(base)

  def apply(): Case = new Case()
}
