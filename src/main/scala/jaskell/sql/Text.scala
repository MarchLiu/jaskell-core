package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/19 17:20
 */
class Text(val prefix:Directive = new Empty, val string:String) extends Directive with CouldAs
  with CouldBeColumn with Expression {
  val content: String = string.replace("'", "\\'")
  override def script: String = s"${prefix.script} '$content'"

  override def parameters: Seq[Parameter[_]] = prefix.parameters
}
