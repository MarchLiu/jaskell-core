package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 20:18
 */
trait As extends Directive with Expression with CouldBeColumn with CouldBeFrom with CouldBeJoin {
  val prefix: Directive
  val name: Name

  override def script: String = s"${prefix.script} AS ${name.script}"

  override def parameters: Seq[Parameter[_]] = prefix.parameters ++ name.parameters
}
