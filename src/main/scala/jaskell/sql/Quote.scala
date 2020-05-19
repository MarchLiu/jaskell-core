package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 20:09
 */
trait Quote extends Directive with CouldAs {
  val segment: Directive

  override def script: String = s"(${segment.script})"

  override def parameters: Seq[Parameter[_]] = segment.parameters
}

object Quote {
  def apply(p: Directive, condition: Condition): Quote = new Quote with Condition {
    override val prefix: Directive = p
    override val segment: Directive = condition
  }

  def apply(p: Directive, query: Query): Quote = new Quote with Query {
    override val prefix: Directive = p
    override val segment: Directive = query
  }

}
