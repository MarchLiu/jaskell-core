package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 17:27
 */
trait Where extends Directive with CouldBeQuote with Query with CouldUnion {
  val prefix: Directive
  val condition: Directive

  override def script: String = prefix.script + " WHERE " + condition.script

  override def parameters: Seq[Parameter[_]] = prefix.parameters ++ condition.parameters
}
