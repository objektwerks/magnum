package objektwerks

import com.typesafe.config.ConfigFactory

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class StoreTest extends AnyFunSuite with Matchers:
  test("store") {
    val conf = ConfigFactory.load("test.conf")
    val store = Store(conf)
    var todo = Todo(0, "")

    store.count() shouldBe 0

    val todoBuilder = TodoBuilder(task = "wash car")
    todo = store.addTodo(todoBuilder)
    println(s"*** Add Todo: $todo")
    todo.id shouldBe 1

    todo = todo.copy(task = "wash and dry car")
    val updated = store.updateTodo(todo)
    println(s"*** Update Todo: $todo")
    updated shouldBe true

    val count = store.count()
    println(s"*** Todo count should be 1 - but is $count! Why?")

    val todos = store.listTodos()
    println(s"*** List Todos: ${todos.toString}")
    todos.length shouldBe 1
  }