package jaskell.sql

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/19 19:36
 */
class SelectSpec extends AnyFlatSpec with Matchers {
  import jaskell.sql.SQL._

  "Basic Select" should "Test basic SQL generate" in {
    val s = select("f0", "f1", "f2")
    val j = s from "table"
    val query = j where (q(n("f1") > n("f0")) and q(n("f1") > n("f2")))
    query.script should be
      ("SELECT f0, f1, f2 FROM table WHERE (f1 > f0) AND (f1 > f2)")
  }

  "Auto Comprehension " should "Test types implicits comprehension" in {
    val s = select("f0", "f1", "f2")
    val j = s from "table" leftJoin ("table2" as "data") on (n("data.id") == n("table.id"))
    val query = j where (n("f1") > n("f0") and n("f1") > n("f2"))
    query.script should be
    ("SELECT f0, f1, f2 FROM table LEFT JOIN table2 AS data ON data.id = table.id WHERE (f1 > f0) AND (f1 > f2)")
  }

}
