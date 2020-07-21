package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/07/10 16:18
 */
trait Having extends Directive with CouldBeQuote with Query
  with CouldUnion
  with CouldOrder
  with CouldLimit
  with CouldOffset {
  val prefix: Directive
  val condition: Directive

  override def script: String = prefix.script + " HAVING " + condition.script

  override def parameters: Seq[Parameter[_]] = prefix.parameters ++ condition.parameters

}
