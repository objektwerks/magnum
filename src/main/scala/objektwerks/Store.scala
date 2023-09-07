package objektwerks

import com.typesafe.config.Config

object Store {
  def apply(conf: Config): Store = new Store(conf)
}

class Store(conf: Config) {
  implicit val ctx = new H2JdbcContext(SnakeCase, conf.getConfig("quill.ctx"))
  import ctx._

  def addTodo(todo: Todo): Int = run( query[Todo].insert(lift(todo)).returningGenerated(_.id) )

  def updateTodo(todo: Todo): Unit = {
    run( query[Todo].filter(_.id == lift(todo.id)).update(lift(todo)) )
    ()
  }

  def listTodos(): Seq[Todo] = run( query[Todo] )
}