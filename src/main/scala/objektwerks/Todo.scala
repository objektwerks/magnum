package objektwerks

import com.augustnagro.magnum.{DbCodec, H2DbType, Id, Repo, SqlNameMapper, Table, TableInfo}

@Table(H2DbType, SqlNameMapper.SameCase)
final case class Todo(@Id id: Int, task: String) derives DbCodec

final case class TodoBuilder(task: String) derives DbCodec

val info = TableInfo[TodoBuilder, Todo, Int]

final class TodoRepo extends Repo[TodoBuilder, Todo, Int]