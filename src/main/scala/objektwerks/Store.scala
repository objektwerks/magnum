package objektwerks

import com.augustnagro.magnum.{connect, DbCon, DbTx, transact}
import com.typesafe.config.Config

import java.nio.file.Files
import java.nio.file.Path
import javax.sql.DataSource

import org.h2.jdbcx.JdbcDataSource

import scala.util.Using

private object Store:
  def createDataSource(conf: Config): DataSource =
    val ds = JdbcDataSource()
    ds.setURL( conf.getString("ds.url") )
    ds.setUser( conf.getString("ds.user") )
    ds.setPassword( conf.getString("ds.password") )

    Using.Manager(use =>
      val connection = use( ds.getConnection )
      val statement = use( connection.createStatement )
      val sql = Files.readString( Path.of("ddl.sql") )
      statement.execute(sql)
    )
    ds

final class Store(conf: Config):
  private given ds: DataSource = Store.createDataSource(conf)

  private val delegate = Delegate()

  def count(): Long =
    connect(ds):
      delegate.count()

  def addTodo(todo: TodoBuilder): Todo =
    transact(ds):
      delegate.addTodo(todo)

  def updateTodo(todo: Todo): Boolean =
    transact(ds):
      delegate.updateTodo(todo)

  def listTodos(): Vector[Todo] =
    connect(ds):
      delegate.listTodos()

private final class Delegate():
  val repo = TodoRepo()

  def count()(using DbCon): Long = repo.count

  def addTodo(todo: TodoBuilder)(using DbTx): Todo = repo.insertReturning(todo)

  def updateTodo(todo: Todo)(using DbTx): Boolean =
    repo.update(todo)
    true

  def listTodos()(using DbCon): Vector[Todo] = repo.findAll