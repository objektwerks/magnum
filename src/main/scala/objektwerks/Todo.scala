package objektwerks

import com.augustnagro.magnum.{DbCodec, H2DbType, Id, Repo, SqlNameMapper, Table, TableInfo}

import java.time.Instant

@Table(H2DbType, SqlNameMapper.SameCase)
final case class Todo(@Id id: Int, task: String, created: Long, completed: Long) derives DbCodec

final case class TodoBuilder(task: String, created: Long = Instant.now.getEpochSecond, completed: Long = 0) derives DbCodec

final class TodoRepo extends Repo[TodoBuilder, Todo, Int]

val info = TableInfo[TodoBuilder, Todo, Int]