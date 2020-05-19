package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 18:56
 */
trait On extends Directive with CouldJoin with CouldGroup with CouldOrder with CouldLimit with CouldOffset
    with CouldCondition {
  val prefix: Directive
  val condition: Condition

  override def script: String = {
    prefix.script + " ON " + condition.script
  }

  override def parameters: Seq[Parameter[_]] = prefix.parameters ++ condition.parameters
}
