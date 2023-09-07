package objektwerks

import com.augustnagro.magnum.{DbCodec, H2DbType, Id, Repo, SqlNameMapper, Table, TableInfo}

final case class TodoBuilder(task: String) derives DbCodec

@Table(H2DbType, SqlNameMapper.CamelToSnakeCase)
final case class Todo(@Id id: Int = 0, task: String) derives DbCodec

final class TodoRepo extends Repo[TodoBuilder, Todo, Int]

val info = TableInfo[TodoBuilder, Todo, Int]