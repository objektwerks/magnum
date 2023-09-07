package objektwerks

import com.augustnagro.magnum.*
import com.typesafe.config.Config

import org.h2.jdbcx.JdbcDataSource

final class Store(conf: Config):
  val ds = JdbcDataSource()
  ds.setURL(conf.getString("url"))
  ds.setUser(conf.getString("user"))
  ds.setPassword(conf.getString("password"))

  val repo = TodoRepo()

  def addTodo(todo: TodoBuilder): Todo =
    transact(ds):
      repo.insertReturning(todo)

  def updateTodo(todo: Todo): Boolean =
    transact(ds):
      repo.update(todo)
      true

  def listTodos(): Seq[Todo] = ???