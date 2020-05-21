package jaskell.sql

import java.sql.{Connection, DriverManager}

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Using

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/21 15:35
 */
class WriteSpec extends AnyFlatSpec with Matchers {

  import jaskell.sql.SQL._

  val url = "jdbc:sqlite::memory:"
  val table: Name with CouldBeFrom = t("test")
  val conn = DriverManager.getConnection(url)


  val create = conn.prepareStatement("create table test(id integer primary key autoincrement, content text)")
  create.execute()
  create.close()

  val query = insert.into(table)(c("content")).values(p("data")).cache
  val statement = query.prepare(conn)
  statement.execute()

  "Insert" should "Test insert " in {
    val query = insert.into(table)(c("content")).values(p("data")).cache
    val statement = query.prepare(conn)

    for (i <- 0 until 10) {
      val log = String.format("write %dth log", i)
      query.setParameter("data", log)
      query.syncParameters(statement)
      statement.execute
      statement.getUpdateCount should be(1)
    }
  }

  "Update" should "Test update" in {
    var id = 0
    val findIdQuery = select(max(n("id")).as("id")).from(table)
    val i: Option[Int] = findIdQuery.scalar[Int](conn)
    id = i.get
    val statement =
      update(table).set(c("content"), p("data")).where(n("id") == p[Int]("id"))
    statement.setParameter("id", id)
    statement.setParameter("data", "rewritten")
    statement.execute(conn)

    val query = select("content").from(table).where(n("id") == l(id))
    val s = query.scalar[String](conn)
    s should be(Some("rewritten"))
  }

  "Clean" should "Test delete " in {
    val statement = delete.from(table)
    statement.execute(conn)
  }
}
