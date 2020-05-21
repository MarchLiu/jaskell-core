package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/19 17:17
 */
trait Literal extends Directive with CouldAs with CouldBeCondition with CouldBeColumn with Expression {
  val prefix : Directive
  val literal: String

  override def script: String = s"${prefix.script} $literal ".trim

  override def parameters: Seq[Parameter[_]] = prefix.parameters
}
