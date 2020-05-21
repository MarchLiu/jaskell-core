package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 16:03
 */
class Name(val name: String, var quoted: Boolean = false) extends Directive with Expression with CouldAs
  with CouldBeCondition with CouldBeColumn {
  override def script: String = {
    if (name.contains('"') || quoted) {
      "\"%s\"".format(script.replace("\"", "\\\""))
    } else {
      name
    }
  }

  override def parameters: Seq[Parameter[_]] = {
    Seq.empty
  }

  def isNull: Expression with Condition = new Expression with Condition {
    override def script: String = s"${Name.this.script} IS NULL"

    override def parameters: Seq[Parameter[_]] = Name.this.parameters
  }

  def isNotNull: Expression with Condition = new Expression with Condition {
    override def script: String = s"${Name.this.script} IS NOT sNULL"

    override def parameters: Seq[Parameter[_]] = Name.this.parameters
  }
}
