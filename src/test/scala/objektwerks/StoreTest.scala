package objektwerks

import com.typesafe.config.ConfigFactory

import org.scalatest.funsuite.AnyFunSuite

class StoreTest extends AnyFunSuite {
  test("store") {
    val conf = ConfigFactory.load("test.conf")
    val store = Store(conf)

    val todoBuilder = TodoBuilder(task = "wash car")
    val todo = store.addTodo(todoBuilder)
    println(s"*** Add Todo: $todo")
    assert( todo.id == 1 )

    val updated = store.updateTodo(todo.copy(task = "wash and dry car"))
    println(s"*** Update Todo: $todo")
    assert(updated)

    val todos = store.listTodos()
    println(s"*** List Todos: ${todos.toString}")
    assert( todos.length == 1 )
  }
}