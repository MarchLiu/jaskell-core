package jaskell.sql

import scala.collection.mutable

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 15:49
 */
class Select extends Statement with CouldFrom with Query with CouldUnion {

  val columns: mutable.ListBuffer[CouldBeColumn] = new mutable.ListBuffer[CouldBeColumn]
  var isDistinct: Boolean = false

  override def script: String = {
    val sb: mutable.StringBuilder = new mutable.StringBuilder("SELECT ")
    if(isDistinct){
      sb ++= "DISTINCT "
    }
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

  def distinct: this.type  = {
    isDistinct = true
    this
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
    with CouldLimit with CouldOffset with CouldBeJoin with CouldUnion {
  }

}