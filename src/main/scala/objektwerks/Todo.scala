package objektwerks

import com.augustnagro.magnum.{DbCodec, H2DbType, Id, ImmutableRepo, SqlNameMapper, Table, TableInfo}

@Table(H2DbType, SqlNameMapper.CamelToSnakeCase)
final case class Todo(@Id id: Int = 0, task: String) derives DbCodec

final class TodoRepo extends ImmutableRepo[Todo, Int]

object Todo:
  val info = TableInfo[Todo, Todo, Int]
  val repo = TodoRepo()