package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/19 18:57
 */
object SQL {
  import scala.util.matching.Regex
  val stringRegex: Regex = "^''$".r
  def empty = new Empty

  def select: Select = new Select
  def insert: Insert = new Insert
  def update(name: Name): Update = new Update(name)
  def delete: Delete = new Delete

  def n(name: String): Name = new Name(name)
  def t(name: String): Name with CouldBeJoin with CouldWhere with CouldBeFrom with CouldOrder with CouldGroup =
    new Name(name) with CouldBeJoin with CouldWhere with CouldBeFrom with CouldOrder with CouldGroup

  def c(name: String): Name with CouldBeColumn = new Name(name) with CouldBeColumn
  def l(ltl: String): Literal = new Literal {
    override val prefix: Directive = empty
    override val literal: String = ltl
  }
  def l(ltl: Int): Literal = new Literal {
    override val prefix: Directive = empty
    override val literal: String = ltl.toString
  }

  def q(condition: Condition): Quote = new Quote with Condition {
    override val segment: Condition = condition
  }

  def q(qry: Query): Quote = new Quote with Query {
    override val segment: Query = qry
  }

  def q(expression: Expression): Quote = new Quote with Expression {
    override val segment: Expression = expression
  }

  def p[T](name: String): Parameter[T] = {
    val parameter = new Parameter[T]
    parameter.key = name
    parameter
  }

  def max(argument: Directive): Func = {
    Func("max", argument)
  }

  def min(argument: Directive): Func = {
    Func("max", argument)
  }

  def avg(argument: Directive): Func = {
    Func("avg", argument)
  }

  def count: Func = {
    Func("count", n("*"))
  }

  def count(argument: Directive): Func = {
    Func("count", argument)
  }

  def f(name:String, args: Directive*): Func = {
    Func(name, args :_*)
  }

  def `case`: Case = {
    new Case()
  }

  def `case`(base: Expression): Case = {
    new Case(base)
  }

  implicit def strToName(str:String):Expression = stringRegex.findFirstIn(str) match {
    case Some(txt) => new Text(string = txt)
    case None => new Name(str)
  }

  implicit def strsToNames(strs: Seq[String]):Seq[Expression] = strs map strToName
}
