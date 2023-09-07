package objektwerks

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import java.util.UUID
import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

object Performance extends LazyLogging {
  val conf = ConfigFactory.load("app.conf")
  val store = Store(conf)
  logger.info("Database and Store initialized for performance testing.")
}

@State(Scope.Thread)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@Fork(1)
class Performance() {
  import Performance.store

  var todoBuilder = TodoBuilder(task = UUID.randomUUID.toString)
  val todo = store.addTodo(todoBuilder)

  @Benchmark
  def addTodo(): Int = store.addTodo(Todo(task = UUID.randomUUID.toString))

  @Benchmark
  def updateTodo(): Unit = {
    todo = todo.copy(task = UUID.randomUUID.toString)
    store.updateTodo(todo)
  }

  @Benchmark
  def listTodos(): Seq[Todo] = store.listTodos()
}