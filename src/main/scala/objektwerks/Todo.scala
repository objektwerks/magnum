package objektwerks

import com.augustnagro.magnum.{H2DbType, Id, ImmutableRepo, SqlNameMapper, Table, TableInfo}

@Table(H2DbType, SqlNameMapper.CamelToSnakeCase)
final case class Todo(@Id id: Int = 0, task: String)

object Todo:
  val repo = ImmutableRepo[Todo, Int]
  val info = TableInfo[Todo, Todo, Int]