package objektwerks

import com.typesafe.config.Config

import org.h2.jdbcx.JdbcDataSource

final class Store(conf: Config):
  val ds = JdbcDataSource()
  ds.setURL(conf.getString("url"))
  ds.setUser(conf.getString("user"))
  ds.setPassword(conf.getString("password"))