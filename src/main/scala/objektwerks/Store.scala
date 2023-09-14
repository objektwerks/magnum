package objektwerks

import com.augustnagro.magnum.{connect, DbCon, DbTx, transact}
import com.typesafe.config.Config

import java.nio.file.Files
import java.nio.file.Path
import javax.sql.DataSource

import org.h2.jdbcx.JdbcConnectionPool

import scala.util.Using

private object Store:
  def createDataSource(config: Config): DataSource =
    val ds = JdbcConnectionPool.create(
      config.getString("ds.url"),
      config.getString("ds.user"),
      config.getString("ds.password")
    )

    Using.Manager( use =>
      val connection = use( ds.getConnection )
      val statement = use( connection.createStatement )
      val sql = Files.readString( Path.of( config.getString("ds.ddl") ) )
      statement.execute(sql)
    )
    ds

final class Store(config: Config):
  private val ds: DataSource = Store.createDataSource(config)
  private val delegate = Delegate()

  def close(): Unit = ds.asInstanceOf[JdbcConnectionPool].dispose()

  def count(): Long =
    connect(ds):
      delegate.count()

  def addTodo(todoBuilder: TodoBuilder): Todo =
    transact(ds):
      delegate.addTodo(todoBuilder)

  def updateTodo(todo: Todo): Boolean =
    transact(ds):
      delegate.updateTodo(todo)

  def deleteTodo(todo: Todo): Boolean =
    transact(ds):
      delegate.deleteTodo(todo)

  def listTodos(): Vector[Todo] =
    connect(ds):
      delegate.listTodos()

private final class Delegate():
  private val todoRepo = TodoRepo()

  def count()(using DbCon): Long = todoRepo.count

  def addTodo(todoBuilder: TodoBuilder)(using DbTx): Todo =
    todoRepo.insertReturning(todoBuilder)

  def updateTodo(todo: Todo)(using DbTx): Boolean =
    todoRepo.update(todo)
    true

  def deleteTodo(todo: Todo)(using DbTx): Boolean =
    todoRepo.delete(todo)
    true

  def existsById(id: Int)(using DbCon): Boolean =
    todoRepo.existsById(id)

  def listTodos()(using DbCon): Vector[Todo] = todoRepo.findAll