package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/19 18:26
 */
trait In extends Directive {
  val prefix: Directive
  val quote: Quote

  override def script: String = s"${prefix.script} IN ${quote.script}"

  override def parameters: Seq[Parameter[_]] = prefix.parameters ++ quote.parameters
}
