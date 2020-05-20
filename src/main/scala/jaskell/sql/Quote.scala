package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 20:09
 */
trait Quote extends Directive with CouldAs with CouldBeCondition with Condition {
  val segment: CouldBeQuote

  override def script: String = s"(${segment.script})"

  override def parameters: Seq[Parameter[_]] = segment.parameters

}

object Quote {
  def apply(condition: Condition): Quote = new Quote with Condition {
    override val segment: Condition = condition
  }

  def apply(query: Query): Quote = new Quote with Query {
    override val segment: Query = query
  }

  def apply(expression: Expression): Quote = new Quote with Expression {
    override val segment: Expression = expression
  }

}
