package objektwerks

import com.augustnagro.magnum.transact
import com.typesafe.config.Config

import java.nio.file.Files
import java.nio.file.Path
import java.sql.Connection

import org.h2.jdbcx.JdbcDataSource

import scala.util.Using


final class Store(conf: Config):
  val ds = JdbcDataSource()
  val url = conf.getString("ds.url")
  val user = conf.getString("ds.user")
  val password = conf.getString("ds.password")

  val success = Using.Manager(use =>
    val con = use(ds.getConnection)
    val stmt = use(con.createStatement)
    val sql = Files.readString(Path.of("ddl.sql"))
    println("executing \n" + sql)
    stmt.execute(sql)
  ).get
  println( s"*** Loaded database ddl.sql successfully: $success")

  val repo = TodoRepo()

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