Magnum
------
>Magnum feature tests with H2 and Scala 3.

Warning
-------
>Magnum ***findAll*** currently returns **zero** results from an **H2** memory or file database.

>The following **H2** config is used:
```
ds {
  # url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;INIT=runscript from './ddl.sql'"
  url = "jdbc:h2:file:./target/db;DB_CLOSE_DELAY=-1;INIT=runscript from './ddl.sql'"
  user = "sa"
  password = "sa"
}
```
>Both ***urls*** yield the same test result.

>**See** [Magnum Issue 17](https://github.com/AugustNagro/magnum/issues/17)

Build
-----
1. sbt clean compile

Test
----
1. sbt clean test

Benchmark
---------
>See Performance class for details.
1. sbt jmh:run
>**Warning:** Using JDK20, throws: java.lang.ClassNotFoundException: java.sql.ResultSet

>**See:** [Benchmark compilation fails if java.sql.ResultSet is used #192](https://github.com/sbt/sbt-jmh/issues/192)

Results
-------
>OpenJDK Runtime Environment Zulu20.32+11-CA (build 20.0.2+9)
1. addTodo - 0.0
2. listTodos - 0.0
3. updateTodo - 0.0
>Total time: 0 s (0:0), 10 warmups, 10 iterations, in microseconds, completed 2023.9.7

Resources
---------
* [Magnum Github](https://github.com/AugustNagro/magnum)
* [Scala Days, Seattle, 2023](https://www.youtube.com/watch?v=iKNRS5b1zAY)