package jaskell.sql

import jaskell.sql.Insert.Into

import scala.collection.mutable

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/20 18:28
 */
class Insert extends Directive {
  def into(name: Name): Into = new Into(name)

  override def script: String = ""

  override def parameters: Seq[Parameter[_]] = Seq.empty
}

object Insert {

  class Into(val name: Name) extends Directive {
    val columns: mutable.ListBuffer[Name] = new mutable.ListBuffer[Name]

    def apply(names: Name*): this.type = {
      columns ++= names
      this
    }

    def values(expression: Expression *): Values = new Values(this, expression)

    def select: Select = new Select(this)

    override def script: String = {
      val cols = columns.map(_.script).mkString(",");
      s"INSERT INTO ${name.script}($cols)"
    }

    override def parameters: Seq[Parameter[_]] = name.parameters ++ columns.flatMap(_.parameters)
  }

  class Select(val prefix: Directive) extends jaskell.sql.Select {
    override def script: String = prefix.script + " " + super.script

    override def parameters: Seq[Parameter[_]] = prefix.parameters ++ super.parameters
  }

  class Values(val prefix: Directive, val values: Seq[Expression]) extends Directive with CouldReturning with Statement {
    override def script: String = {
      val vs = values.map(_.script).mkString(", ")
      s"${prefix.script} VALUES($vs)"
    }

    override def parameters: Seq[Parameter[_]] = prefix.parameters ++ values.flatMap(_.parameters)
  }

}
