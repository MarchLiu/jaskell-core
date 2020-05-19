package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 19:39
 */
trait Is extends Directive {
  val prefix: Directive

  def NULL(): Is.Null = new Is.Null {
    override val prefix: Directive = this
  }

  override def script: String = prefix.script + " IS"

  override def parameters: Seq[Parameter[_]] = prefix.parameters
}

object Is {
  trait Null extends Condition {
    val prefix:Directive
    override def script: String = prefix.script + " NULL"

    override def parameters: Seq[Parameter[_]] = prefix.parameters
  }
}
