package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 18:56
 */
trait Join extends Directive {
  val prefix: Directive
  val j: CouldBeJoin


  override def script: String = {
    prefix.script + " JOIN " + j.script
  }

  override def parameters: Seq[Parameter[_]] = prefix.parameters ++ j.parameters

  def on(cond: Condition): On with CouldWhere = new On with CouldWhere {
    override val prefix: Directive = this
    override val condition: Condition = cond
  }
}
