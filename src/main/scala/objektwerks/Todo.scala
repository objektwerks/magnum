package objektwerks

import com.augustnagro.magnum.{H2DbType, SqlNameMapper, Table}

@Table(H2DbType, SqlNameMapper.CamelToSnakeCase)
case class Todo(id: Int = 0, task: String)