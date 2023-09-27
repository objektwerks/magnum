package objektwerks

import com.augustnagro.magnum.{connect, DbCon, DbTx, Spec, transact}
import com.typesafe.config.Config

import java.nio.file.{Files, Path}
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
  private val todoRepo = TodoRepo()
  private val delegate = Delegate(todoRepo)

  def close(): Unit =
    ds.asInstanceOf[JdbcConnectionPool].dispose()

  def countTodos(): Long =
    connect(ds):
      delegate.countTodos()

  def addTodo(todoBuilder: TodoBuilder): Todo =
    transact(ds):
      delegate.addTodo(todoBuilder)

  def updateTodo(todo: Todo): Boolean =
    transact(ds):
      delegate.updateTodo(todo)

  def deleteTodo(todo: Todo): Boolean =
    transact(ds):
      delegate.deleteTodo(todo)

  def todoExistsById(id: Int): Boolean =
    connect(ds):
      delegate.todoExistsById(id)

  def findTodoById(id: Int): Option[Todo] =
    connect(ds):
      delegate.findTodoById(id)

  def listTodos(): Vector[Todo] =
    connect(ds):
      delegate.listTodos()

  def listTodos(orderBy: OrderBy): Vector[Todo] =
    connect(ds):
      delegate.listTodos(orderBy.getSpec())

  def listCompletedTodos(): Vector[Todo] =
    connect(ds):
      delegate.listCompletedTodos()

private final class Delegate(todoRepo: TodoRepo):
  def countTodos()(using DbCon): Long =
    todoRepo.count

  def addTodo(todoBuilder: TodoBuilder)(using DbTx): Todo =
    todoRepo.insertReturning(todoBuilder)

  def updateTodo(todo: Todo)(using DbTx): Boolean =
    todoRepo.update(todo)
    true

  def deleteTodo(todo: Todo)(using DbTx): Boolean =
    todoRepo.delete(todo)
    true

  def todoExistsById(id: Int)(using DbCon): Boolean =
    todoRepo.existsById(id)

  def findTodoById(id: Int)(using DbCon): Option[Todo] =
    todoRepo.findById(id)

  def listTodos()(using DbCon): Vector[Todo] =
    todoRepo.findAll

  def listTodos(spec: Spec[Todo])(using DbCon): Vector[Todo] =
    todoRepo.findAll(spec)

  def listCompletedTodos()(using DbCon): Vector[Todo] =
    todoRepo.completedTodosQuery.run()