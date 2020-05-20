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

  def n(name: String): Name = new Name(name)
  def c(name: String): Name with CouldBeColumn = new Name(name) with CouldBeColumn
  def l(ltl: String): Literal = new Literal {
    override val prefix: Directive = empty
    override val literal: String = ltl
  }

  def q(condition: Condition): Quote = new Quote with Condition {
    override val segment: Condition = condition
  }

  def q(query: Query): Quote = new Quote with Query {
    override val segment: Query = query
  }

  def q(expression: Expression): Quote = new Quote with Expression {
    override val segment: Expression = expression
  }

  implicit def strToName(str:String):Expression = stringRegex.findFirstIn(str) match {
    case Some(txt) => new Text(string = txt)
    case None => new Name(str)
  }

  implicit def strsToNames(strs: Seq[String]):Seq[Expression] = strs map strToName
}
