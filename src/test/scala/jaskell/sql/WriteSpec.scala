package jaskell.sql

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.sql.{Connection, DriverManager, PreparedStatement}

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
  val conn: Connection = DriverManager.getConnection(url)


  val create: PreparedStatement = conn.prepareStatement("create table test(id integer primary key autoincrement, content text)")
  create.execute()
  create.close()

  val query: Statement = insert.into(table)(c("content")).values(p("data")).cache
  val statement: PreparedStatement = query.prepare(conn)
  statement.execute()

  "Insert" should "Test insert " in {
    val query = insert.into(table)(c("content")).values(p("data")).cache
    val statement = query.prepare(conn)

    for (i <- 0 until 10) {
      val log = f"write ${i}th log"
      query.setParameter("data", log)
      query.syncParameters(statement)
      statement.execute
      statement.getUpdateCount should be(1)
    }
  }

  "Update" should "Test update" in {
    val findIdQuery = select(max(n("id")).as("id")).from(table)
    val i: Option[Int] = findIdQuery.scalar[Int](conn)
    val id = i.get
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
