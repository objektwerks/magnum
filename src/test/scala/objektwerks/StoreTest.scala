package objektwerks

import com.typesafe.config.ConfigFactory

import org.scalatest.funsuite.AnyFunSuite

class StoreTest extends AnyFunSuite:
  test("store") {
    val conf = ConfigFactory.load("test.conf")
    val store = Store(conf)

    val todoBuilder = TodoBuilder(task = "wash car")
    var todo = store.addTodo(todoBuilder)
    println(s"*** Add Todo: $todo")
    assert( todo.id == 1 )

    todo = todo.copy(task = "wash and dry car")
    val updated = store.updateTodo(todo)
    println(s"*** Update Todo: $todo")
    assert(updated)

    store.count()
    val todos = store.listTodos()
    println(s"*** List Todos: ${todos.toString}")
    assert( todos.length == 1 )
  }