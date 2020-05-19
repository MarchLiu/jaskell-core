package jaskell.sql

import scala.collection.mutable

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 15:49
 */
class Select extends Statement {
  val columns: mutable.MutableList[Expression] = new mutable.MutableList[Expression]

  override def script: String = {
    val sb: mutable.StringBuilder = new mutable.StringBuilder("SELECT ")
    sb ++= columns.map(_.script).mkString(", ")
    sb.mkString
  }

  override def parameters: Seq[Parameter[_]] = {
    val re = columns.flatMap(_.parameters).toSeq
    for (idx <- re.indices) {
      re(idx).order(idx + 1)
    }
    re
  }

  def from(f: CouldBeFrom): Select.From = new Select.From {
    override def script: String = this.script + " FROM " + f.script

    override def parameters: Seq[Parameter[_]] = this.parameters ++ f.parameters
  }
}

object Select {
  trait From extends jaskell.sql.From with CouldGroup with CouldOrder
    with CouldLimit with CouldOffset with CouldBeJoin {
  }
}