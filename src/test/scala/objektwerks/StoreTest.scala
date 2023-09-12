package objektwerks

import com.augustnagro.magnum.{connect, transact}
import com.typesafe.config.ConfigFactory

import javax.sql.DataSource

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class StoreTest extends AnyFunSuite with Matchers:
  test("store") {
    val conf = ConfigFactory.load("test.conf")
    val store = Store(conf)

    given ds: DataSource = store.ds

    connect(ds):
      store.count() shouldBe 0

    val todoBuilder = TodoBuilder(task = "wash car")
    val todo =
      transact(ds):
        store.addTodo(todoBuilder)
    println(s"*** Add Todo: $todo")
    todo.id shouldBe 1

    val updatedTodo = todo.copy(task = "wash and dry car")
    val updated =
      transact(ds):
        store.updateTodo(updatedTodo)
    println(s"*** Update Todo: $updatedTodo")
    updated shouldBe true

    transact(ds):
      store.count() shouldBe 1

    val todos =
      connect(ds):
        store.listTodos()
    println(s"*** List Todos: ${todos.toString}")
    todos.length shouldBe 1
  }