package objektwerks

import com.typesafe.config.ConfigFactory

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class StoreTest extends AnyFunSuite with Matchers:
  val conf = ConfigFactory.load("test.conf")
  val store = Store(conf)

  test("store") {
    count() shouldBe 0

    val todo = addTodo( TodoBuilder(task = "wash car") )
    todo.id shouldBe 1

    val updatedTodo = todo.copy(task = "wash and dry car")
    val updated = store.updateTodo(updatedTodo)
    println(s"*** Update Todo: $updatedTodo")
    updated shouldBe true

    count() shouldBe 1

    val todos = store.listTodos()
    println(s"*** List Todos: ${todos.toString}")
    todos.length shouldBe 1
  }

  def count(): Long = store.count()

  def addTodo(todoBuilder: TodoBuilder): Todo =
    val todo = store.addTodo(todoBuilder)
    println(s"*** Add Todo: $todo")
    todo