package jaskell.sql

import jaskell.sql

import scala.collection.mutable

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/20 18:00
 */
class Update(val name: Name) extends Directive with CouldWhere with CouldReturning with CouldBeQuote {
  val pairs: mutable.ListBuffer[(Name, Expression)] = new mutable.ListBuffer[(Name, Expression)]

  def set(name: Name, exp: Expression): this.type = {
    pairs += Tuple2(name, exp)
    this
  }

  override def where(c: Condition): Where with CouldReturning = new Where with CouldReturning {
    override val prefix: Directive = Update.this
    override val condition: Directive = c
  }

  override def script: String =
    s"UPDATE ${name.script} SET " + (pairs.map({ pair =>
      val (n, exp) = pair
      s"${n.script} = ${exp.script}"
    }) mkString ", ")

  override def parameters: Seq[Parameter[_]] = name.parameters ++ pairs.flatMap({ pair =>
    val (n, exp) = pair
    n.parameters ++ exp.parameters
  })
}
