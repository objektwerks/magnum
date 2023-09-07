package objektwerks

import com.typesafe.config.ConfigFactory

import org.scalatest.funsuite.AnyFunSuite

class StoreTest extends AnyFunSuite:
  test("store") {
    val conf = ConfigFactory.load("test.conf")
    val store = Store(conf)

    assert( store.count() == 0 )

    val todoBuilder = TodoBuilder(task = "wash car")
    var todo = store.addTodo(todoBuilder)
    println(s"*** Add Todo: $todo")
    assert( todo.id == 1 )

    todo = todo.copy(task = "wash and dry car")
    val updated = store.updateTodo(todo)
    println(s"*** Update Todo: $todo")
    assert(updated)

    val count = store.count()
    println(s"*** Todo count should be 1: $count") // If count == 0, H2 and/or Magnum are failing.

    val todos = store.listTodos()
    println(s"*** List Todos: ${todos.toString}")
    assert( todos.length == 1 )
  }