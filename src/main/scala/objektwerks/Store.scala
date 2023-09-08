package objektwerks

import com.augustnagro.magnum.transact
import com.typesafe.config.Config

import java.sql.Connection

import org.h2.jdbcx.JdbcDataSource

final class Store(conf: Config):
  val ds = JdbcDataSource()
  val url = conf.getString("ds.url")
  val user = conf.getString("ds.user")
  val password = conf.getString("ds.password")

  println(s"*** url: $url")
  println(s"*** user: $user")
  println(s"*** password: $password")

  ds.setURL(url)
  ds.setUser(user)
  ds.setPassword(password)

  val repo = TodoRepo()

  val connection = ds.getConnection()
  require(count() == 0, "Database connection is invalid!")
  println( s"*** Connection is valid: ${connection.isValid(0)}")
  connection.close()

  private def withRepeatableRead(connection: Connection): Unit =
    connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ)

  def count(): Long =
    transact(ds, withRepeatableRead):
      repo.count

  def addTodo(todo: TodoBuilder): Todo =
    transact(ds):
      repo.insertReturning(todo)

  def updateTodo(todo: Todo): Boolean =
    transact(ds):
      repo.update(todo)
      true

  def listTodos(): Vector[Todo] =
    transact(ds, withRepeatableRead):
      repo.findAll