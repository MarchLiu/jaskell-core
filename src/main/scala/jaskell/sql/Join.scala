package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 18:56
 */
trait Join extends Directive with CouldOn {
  val opt: String
  val prefix: Directive
  val j: CouldBeJoin

  override def script: String = {
    prefix.script + s" $opt JOIN " + j.script
  }

  override def parameters: Seq[Parameter[_]] = prefix.parameters ++ j.parameters


}
