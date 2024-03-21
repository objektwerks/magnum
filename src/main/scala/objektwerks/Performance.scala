package objektwerks

import com.typesafe.config.ConfigFactory

import java.util.UUID
import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations.*

object Performance:
  val conf = ConfigFactory.load("store.conf")
  val store = Store(conf)

@State(Scope.Thread)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@Fork(1)
class Performance(): // This class can't be final!
  import Performance.store

  var todo = Todo(0, "", 0, 0)

  @Benchmark
  def addTodo(): Todo =
    val todoBuilder = TodoBuilder(task = UUID.randomUUID.toString)
    todo = store.addTodo(todoBuilder)
    todo

  @Benchmark
  def updateTodo(): Boolean =
    todo = todo.copy(task = UUID.randomUUID.toString)
    store.updateTodo(todo)

  @Benchmark
  def listTodos(): Vector[Todo] =
    store.listTodos()