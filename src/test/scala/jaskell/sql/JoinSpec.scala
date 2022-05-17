package jaskell.sql

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.sql.{Connection, DriverManager}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/21 11:08
 */
class JoinSpec extends AnyFlatSpec with Matchers {

  import jaskell.sql.SQL._

  private val url: String = "jdbc:sqlite::memory:"
  private val table: String = "test"
  private val ist: Statement = insert.into(n(table))(c("pid"), c("content"))
    .values(p[Int]("pid"), p[String]("content"))
  Class.forName("org.sqlite.JDBC")
  val conn: Connection = DriverManager.getConnection(url)

  conn.prepareStatement(
    "create table test(id integer primary key autoincrement, pid integer references test(id), content text)"
  ).execute


  val ins: Statement = ist.cache
  val queryLastId: Query = select(f("last_insert_rowid"))
  var last: Int = 1
  ins.setParameter("content", "one line.")
  for (_ <- 0 until 10) {
    ins.setParameter("pid", last)
    ins.execute(conn)
    last = queryLastId.scalar[Int](conn).getOrElse(0)
    if (last == 0) {
      throw new IllegalStateException("can't get new id which insert")
    }
  }

  "Init" should "Test database init by DDL" in {
    val q: Query = select(count).from("test")
    val c = q.scalar[Int](conn)
    c should be(Some(10))
  }


  "Join Self" should "Create join self SQL" in {
    val q: Query = select("l.id, r.id, l.content, r.content")
      .from(n("test").as("l"))
      .join(n("test").as("r")).on(l("l.id") == (l("r.pid"))).where(l("l.id") != (l("r.id")))
    val statement = q.prepare(conn)
    val rs = q.query(statement)
    try while ( {
      rs.next
    }) {
      rs.getString(3) should be(rs.getString(4))
      rs.getInt(1) + 1 should be(rs.getInt(2))
    } finally {
      if (statement != null) statement.close()
      if (rs != null) rs.close()
    }
  }

  "LeftJoin0" should "Test left join" in {
    val q: Query = select("l.id, r.id, l.content, r.content").from(n("test").as("l"))
      .leftJoin(n("test").as("r")).on(n("l.id") == n("r.pid")).where(n("r.id").isNull)
    val statement = q.prepare(conn)
    val rs = q.query(statement)
    try while ( {
      rs.next
    }) {
      rs.getObject(2) should be(null)
    } finally {
      if (statement != null) statement.close()
      if (rs != null) rs.close()
    }
  }

  "LeftJoin1" should "Test left join" in {
    val q: Query = select("r.id")
      .from(n("test").as("l")).leftJoin(n("test") as "r").on(n("l.id") == n("r.pid"))
      .where(n("r.id").isNull)
    val re = q.scalar[Int](conn)
    re.isDefined should be(false)
  }

}
