package jaskell.sql

import java.sql.{Connection, PreparedStatement, SQLException}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 15:12
 */
trait Statement extends Directive {
  @throws[SQLException]
  def prepare(connection: Connection): PreparedStatement = {
    connection.prepareStatement(this.script)
  }

  @throws[SQLException]
  @throws[IllegalStateException]
  def execute(connection: Connection): Boolean = {
    val statement: PreparedStatement = connection.prepareStatement(this.script)
    try {
      syncParameters(statement)
      statement.execute()
    } finally {
      if (statement != null) statement.close()
    }
  }

  @throws[SQLException]
  @throws[IllegalStateException]
  def execute(statement: PreparedStatement): Boolean = {
    syncParameters(statement)
    try {
      statement.execute()
    }finally {
      if (statement != null) statement.close()
    }
  }

  @throws[IllegalArgumentException]
  def setParameter[T](key: Any, value: T): Statement = {
    var flag: Boolean = false
    for (parameter <- parameters) {
      if(parameter.key == key) {
        parameter.asInstanceOf[Parameter[T]].set(value)
        flag = true
      }
    }
    if (!flag) {
      throw new IllegalArgumentException("parameter named %s not found".format(key))
    }
    this
  }

  @throws[SQLException]
  def clear(statement: PreparedStatement): Unit = {
    statement.clearParameters()
  }

  @throws[SQLException]
  def syncParameters(statement: PreparedStatement): Unit = {
    for (parameter <- parameters) {
      if (!parameter.confirmed) {
        throw new IllegalStateException("parameter %s has not value".format(parameter.key))
      }
    }
    clear(statement)
    val params = parameters
    setOrder(params)
    for (parameter <- params) { //TODO: overload by parameter.valueClass
      statement.setObject(parameter.order, parameter.value.orNull)
    }
  }

  private[sql] def setOrder(parameters: Seq[Parameter[_]]): Unit = {
    for (i <- parameters.indices) {
      parameters(i).order(i + 1)
    }
  }

  def cache: Statement = {
    val self: Statement = this
    new Statement() {
      final private val _script: String = self.script
      final private val _parameters: Seq[Parameter[_]] = self.parameters

      override

      def script: String = {
        _script
      }

      override

      def parameters: Seq[Parameter[_]] = {
        _parameters
      }
    }
  }

}
