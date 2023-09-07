package objektwerks

import com.augustnagro.magnum.{DbCodec, H2DbType, Id, Repo, SqlNameMapper, Table, TableInfo}

final case class TodoBuilder(task: String) derives DbCodec

@Table(H2DbType, SqlNameMapper.SameCase)
final case class Todo(@Id id: Int, task: String) derives DbCodec

final class TodoRepo extends Repo[TodoBuilder, Todo, Int]

object Todo:
  val info = TableInfo[TodoBuilder, Todo, Int]