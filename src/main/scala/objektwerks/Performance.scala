package objektwerks

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import java.util.UUID
import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations.*

@State(Scope.Thread)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@Fork(1)
final class Performance() extends LazyLogging:
  val conf = ConfigFactory.load("app.conf")
  val store = Store(conf)
  var todo = Todo(0, "")

  logger.info("Database and Store initialized for performance testing.")

  @Benchmark
  def addTodo(): Todo =
    val todoBuilder = TodoBuilder(task = UUID.randomUUID.toString)
    todo = store.addTodo(todoBuilder)
    todo

  @Benchmark
  def updateTodo(): Unit = {
    todo = todo.copy(task = UUID.randomUUID.toString)
    store.updateTodo(todo)
  }

  @Benchmark
  def listTodos(): Seq[Todo] = store.listTodos()