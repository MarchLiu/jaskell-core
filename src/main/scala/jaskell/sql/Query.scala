package jaskell.sql

import java.sql.{Connection, PreparedStatement, ResultSet, SQLException}

import javax.swing.text.Segment

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 20:14
 */
trait Query extends Directive with CouldBeQuote with Statement {
  @throws[SQLException]
  def query(statement: PreparedStatement): ResultSet = {
    syncParameters(statement)
    statement.executeQuery
  }

  @throws[SQLException]
  def scalar[T](conn: Connection): Option[T] = {
    val statement = conn.prepareStatement(this.script)
    val resultSet = statement.executeQuery
    try {
      if (resultSet.next) {
        if (resultSet.getObject(1) != null) {
          return Some(resultSet.getObject(1).asInstanceOf[T])
        }
      }
      None
    } finally {
      if (statement != null) statement.close()
      if (resultSet != null) resultSet.close()
    }
  }
}
