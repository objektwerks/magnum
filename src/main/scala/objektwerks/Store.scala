package objektwerks

import com.augustnagro.magnum.*
import com.typesafe.config.Config

import org.h2.jdbcx.JdbcDataSource

final class Store(conf: Config):
  val ds = JdbcDataSource()
  val url = conf.getString("url")
  val user = conf.getString("user")
  val password = conf.getString("password")

  println(s"*** url: $url")
  println(s"*** user: $user")
  println(s"*** password: $password")

  ds.setURL(url)
  ds.setUser(user)
  ds.setPassword(password)

  val repo = TodoRepo()

  def addTodo(todo: TodoBuilder): Todo =
    transact(ds):
      repo.insertReturning(todo)

  def updateTodo(todo: Todo): Boolean =
    transact(ds):
      repo.update(todo)
      true

  def listTodos(): Seq[Todo] =
    connect(ds):
      repo.findAll