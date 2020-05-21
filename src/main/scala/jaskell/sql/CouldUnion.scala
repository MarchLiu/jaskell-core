package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/21 13:42
 */
trait CouldUnion extends Directive with Query with CouldBeQuote {
  def union(qry: Query): Query = new Query {
    override def script: String = s"${CouldUnion.this.script} UNION ${qry.script}"

    override def parameters: Seq[Parameter[_]] = CouldUnion.this.parameters ++ qry.parameters
  }

  def unionAll(qry: Query): Query = new Query {
    override def script: String = s"${CouldUnion.this.script} UNION ALL ${qry.script}"

    override def parameters: Seq[Parameter[_]] = CouldUnion.this.parameters ++ qry.parameters
  }

}
