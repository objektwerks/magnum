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
    countTodos() shouldBe 0

    val todo = addTodo( TodoBuilder(task = "wash car") )
    todo.id shouldBe 1

    todoExistsById(todo.id) shouldBe true

    findTodoById(todo.id) shouldBe Some(todo)

    val updatedTodo = todo.copy(task = "wash and dry car")
    updateTodo(updatedTodo) shouldBe true

    countTodos() shouldBe 1

    listTodos().length shouldBe 1

    deleteTodo(updatedTodo)

    todoExistsById(todo.id) shouldBe false

    findTodoById(todo.id) shouldBe None

    listTodos().length shouldBe 0

    countTodos() shouldBe 0
  }

  def countTodos(): Long =
    val count = store.countTodos()
    println(s"*** Count Todos: $count")
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

  def todoExistsById(id: Int): Boolean =
    val exists = store.todoExistsById(id)
    println(s"*** Todo Exists by Id($id): $exists")
    exists

  def findTodoById(id: Int): Option[Todo] =
    val found = store.findTodoById(id)
    println(s"*** Find Todo by Id($id): $found")
    found

  def listTodos(): Vector[Todo] =
    val todos = store.listTodos()
    println(s"*** List Todos: ${todos.toString}")
    todos

  def listTodos(orderBy: OrderBy): Vector[Todo] =
    val todos = store.listTodos(orderBy)
    println(s"*** List Todos by Order: ${todos.toString}")
    todos