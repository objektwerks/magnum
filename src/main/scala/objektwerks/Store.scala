package objektwerks

import com.augustnagro.magnum.{DbCon, DbTx}
import com.typesafe.config.Config

import java.nio.file.Files
import java.nio.file.Path

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
  println(s"*** Executed ddl.sql successfully: $success")

  val repo = TodoRepo()

  def count()(using DbCon): Long = repo.count

  def addTodo(todo: TodoBuilder)(using DbTx): Todo = repo.insertReturning(todo)

  def updateTodo(todo: Todo)(using DbTx): Boolean =
    repo.update(todo)
    true

  def listTodos()(using DbCon): Vector[Todo] = repo.findAll