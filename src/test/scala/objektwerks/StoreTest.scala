package objektwerks

import com.typesafe.config.ConfigFactory

import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

final class StoreTest extends AnyFunSuite with Matchers with BeforeAndAfterAll:
  val config = ConfigFactory.load("test.conf")
  val store = Store(config)
  println("*** Store open.")

  override protected def afterAll(): Unit =
    store.close()
    println("*** Store closed.")

  test("store") {
    count() shouldBe 0

    val todo = addTodo( TodoBuilder(task = "wash car") )
    todo.id shouldBe 1

    val updatedTodo = todo.copy(task = "wash and dry car")
    updateTodo(updatedTodo) shouldBe true

    count() shouldBe 1

    listTodos().length shouldBe 1

    deleteTodo(updatedTodo)

    listTodos().length shouldBe 0

    count() shouldBe 0
  }

  def count(): Long =
    val count = store.count()
    println(s"*** Count: $count")
    count

  def addTodo(todoBuilder: TodoBuilder): Todo =
    val todo = store.addTodo(todoBuilder)
    println(s"*** Add Todo: $todo")
    todo

  def updateTodo(updatedTodo: Todo): Boolean =
    val updated = store.updateTodo(updatedTodo)
    println(s"*** Update Todo: $updatedTodo")
    updated

  def deleteTodo(todo: Todo): Boolean =
    val deleted = store.deleteTodo(todo)
    println(s"*** Delete Todo: $todo")
    deleted

  def listTodos(): Vector[Todo] =
    val todos = store.listTodos()
    println(s"*** List Todos: ${todos.toString}")
    todos