package objektwerks

import com.augustnagro.magnum.{DbTx, transact}
import com.typesafe.config.Config

import java.nio.file.Files
import java.nio.file.Path
import java.sql.Connection

import org.h2.jdbcx.JdbcDataSource

import scala.util.Using


final class Store(conf: Config):
  val ds = JdbcDataSource()
  ds.setURL( conf.getString("ds.url") )
  ds.setUser( conf.getString("ds.user") )
  ds.setPassword( conf.getString("ds.password") )

  val success = Using.Manager(use =>
    val connection = use( ds.getConnection )
    val statement = use( connection.createStatement )
    val sql = Files.readString( Path.of("ddl.sql") )
    println("*** Executing ddl.sql ...\n" + sql)
    statement.execute(sql)
  ).get
  println( s"*** Loaded database ddl.sql successfully: $success")

  val repo = TodoRepo()

  private def withRepeatableRead(connection: Connection): Unit =
    connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ)

  def count()(using DbTx): Long =
    transact(ds, withRepeatableRead):
      repo.count

  def addTodo(todo: TodoBuilder)(using DbTx): Todo =
    transact(ds):
      repo.insertReturning(todo)

  def updateTodo(todo: Todo)(using DbTx): Boolean =
    transact(ds):
      repo.update(todo)
      true

  def listTodos()(using DbTx): Vector[Todo] =
    transact(ds, withRepeatableRead):
      repo.findAll