package objektwerks

import com.typesafe.config.ConfigFactory

import org.scalatest.funsuite.AnyFunSuite

class StoreTest extends AnyFunSuite {
  val conf = ConfigFactory.load("test.conf")
  val store = Store(conf)

  test("store") {
    val todoBuilder = TodoBuilder(task = "wash car")
    val todo = store.addTodo(todoBuilder)
    println(s"*** Todo id = ${todo.id}")
    assert( todo.id == 1 )

    val updated = store.updateTodo(todo.copy(task = "wash and dry car"))
    assert(updated)

    val todos = store.listTodos()
    println(s"*** Todos = ${todos.toString}")
    assert( todos.length == 1 )
  }
}