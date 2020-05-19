package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 19:00
 */
trait Condition extends Directive {
  def and(that: Condition): Condition = new Condition {
    override def script: String = this.script + " AND " + that.script
    override def parameters: Seq[Parameter[_]] = this.parameters ++ that.parameters
  }

  def or(that: Condition): Condition = new Condition {
    override def script: String = this.script + " OR " + that.script
    override def parameters: Seq[Parameter[_]] = this.parameters ++ that.parameters
  }

}
