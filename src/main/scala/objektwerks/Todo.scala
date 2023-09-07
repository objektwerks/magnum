package objektwerks

import com.augustnagro.magnum.{DbCodec, H2DbType, Id, ImmutableRepo, SqlNameMapper, Table, TableInfo}

@Table(H2DbType, SqlNameMapper.CamelToSnakeCase)
final case class Todo(@Id id: Int = 0, task: String) derives DbCodec

object Todo:
  val repo = ImmutableRepo[Todo, Int]
  val info = TableInfo[Todo, Todo, Int]