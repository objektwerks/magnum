package objektwerks

import com.augustnagro.magnum.transact
import com.typesafe.config.ConfigFactory

import javax.sql.DataSource
import java.util.UUID
import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations.*

@State(Scope.Thread)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@Fork(1)
final class Performance():
  val conf = ConfigFactory.load("store.conf")
  val store = Store(conf)
  var todo = Todo(0, "")

  given ds: DataSource = store.ds

  println("*** Database and Store initialized for performance testing.")

  @Benchmark
  def addTodo(): Todo =
    val todoBuilder = TodoBuilder(task = UUID.randomUUID.toString)
    todo =
      transact(ds):
        store.addTodo(todoBuilder)
    todo

  @Benchmark
  def updateTodo(): Boolean =
    todo = todo.copy(task = UUID.randomUUID.toString)
    transact(ds):
      store.updateTodo(todo)

  @Benchmark
  def listTodos(): Vector[Todo] =
    transact(ds):
      store.listTodos()