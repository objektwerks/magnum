Magnum
------
>Magnum feature tests with Scala 3.

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

Results
-------
>OpenJDK Runtime Environment (Zulu 8.56.0.23-CA-macos-aarch64) (build 1.8.0_302-b08)
1. addTodo - 21.284
2. listTodos - 3.375
3. updateTodo - 6.536
>Total time: 607 s (10:07), 10 warmups, 10 iterations, in microseconds, completed 2021.11.5

Resources
---------
* [Magnum Github](https://github.com/AugustNagro/magnum)