package objektwerks

import com.augustnagro.magnum.{DbCodec, H2DbType, Id, NullOrder, Repo, SortOrder, Spec, SqlNameMapper, Table, TableInfo}

import java.time.Instant

object Todo:
  given taskOrdering: Ordering[Todo] = Ordering.by[Todo, String](t => t.task)
  given createdOrdering: Ordering[Todo] = Ordering.by[Todo, Long](t => t.created).reverse
  given completedOrdering: Ordering[Todo] = Ordering.by[Todo, Long](t => t.completed)

  val orderByTaskSpec = Spec[Todo]
    .orderBy("task", SortOrder.Asc, NullOrder.Last)
    .build

  val orderByCreatedSpec = Spec[Todo]
    .orderBy("created", SortOrder.Desc, NullOrder.Last)
    .build

  val orderByCompletedSpec = Spec[Todo]
    .orderBy("completed", SortOrder.Asc, NullOrder.Last)
    .build

  val info = TableInfo[TodoBuilder, Todo, Int]

  def epochSecond(): Long = Instant.now.getEpochSecond
  def toInstant(epochSecond: Long): Instant = Instant.ofEpochSecond(epochSecond)

@Table(H2DbType, SqlNameMapper.SameCase)
final case class Todo(@Id id: Int,
                      task: String,
                      created: Long,
                      completed: Long) derives DbCodec

final case class TodoBuilder(task: String,
                             created: Long = Todo.epochSecond(),
                             completed: Long = 0) derives DbCodec

final class TodoRepo extends Repo[TodoBuilder, Todo, Int]