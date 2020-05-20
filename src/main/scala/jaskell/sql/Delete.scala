package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/20 19:17
 */
class Delete extends Directive with CouldFrom {
  override def script: String = s"DELETE "

  override def parameters: Seq[Parameter[_]] = Seq.empty

  override def from(f: CouldBeFrom): From with CouldWhere with Returning = new From with CouldWhere with Returning {

    override val prefix: Directive = Delete.this

    override def script: String = s"${prefix.script} FROM ${f.script}"

    override def parameters: Seq[Parameter[_]] = prefix.parameters ++ f.parameters
  }

  override def from(tableName: String): From = this.from(new Name(tableName) with CouldBeFrom)
}
