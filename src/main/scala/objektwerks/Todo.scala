package objektwerks

import com.augustnagro.magnum.{H2DbType, ImmutableRepo, SqlNameMapper, Table, TableInfo}

@Table(H2DbType, SqlNameMapper.CamelToSnakeCase)
case class Todo(id: Int = 0, task: String)

val todoRepository = ImmutableRepo[Todo, Int]

val todoInfo = TableInfo[Todo, Todo, Int]