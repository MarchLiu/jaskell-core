package jaskell.sql

import scala.collection.mutable

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 15:49
 */
class Select extends Statement with CouldFrom {

  val columns: mutable.MutableList[CouldBeColumn] = new mutable.MutableList

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

  def apply(names: CouldBeColumn *) : this.type = {
    this.columns ++= names
    this
  }

  def +=(name: Any): this.type = {
    this.columns += {
      name match {
      case n:String => new Name(n)
      case c:CouldBeColumn => c
    }}

    this
  }

  def ++=(names: Seq[_]): this.type = {
    for (name <- names) {
      this += name
    }
    this
  }

}


object Select {

  trait From extends jaskell.sql.From with CouldGroup with CouldOrder
    with CouldLimit with CouldOffset with CouldBeJoin {
  }

}