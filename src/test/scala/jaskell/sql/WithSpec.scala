package jaskell.sql

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/21 13:03
 */
class WithSpec extends AnyFlatSpec with Matchers {
  import jaskell.sql.SQL._
  "Basic" should "Generate a with cte " in {
    val script = "WITH t1 AS (SELECT n FROM t), t2 AS (SELECT m FROM t) SELECT m * n FROM t1 JOIN t2 ON t1.n != t2.m"
    val query = ((With(n("t1")) as q(select("n").from("t"))) and ( n("t2"), q(select("m").from("t"))))
      .select(c("m") * c("n"))
      .from("t1").join(t("t2")).on(n("t1.n") != n("t2.m"))
    query.script should be (script)
  }

  "Recursive" should "Generate a recursive common table expression" in {
    val script = "WITH RECURSIVE t(f) AS (SELECT 1 UNION SELECT f + 1 FROM t WHERE f < 100) SELECT f FROM t"
    val s = select (l("f")+l(1)).from("t").where(n("f") < l(100))
    val query = With.recursive.as(n("t(f)"), select (l(1)).union(s)).select("f").from("t")

    query.script should be (script)
  }
}
