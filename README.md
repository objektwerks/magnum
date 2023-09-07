Magnum
------
>Magnum feature tests with H2 and Scala 3.

Warning
-------
>Magnum findAll does not currently work with H2.

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
>OpenJDK Runtime Environment Zulu20.32+11-CA (build 20.0.2+9)
1. addTodo - 0.0
2. listTodos - 0.0
3. updateTodo - 0.0
>Total time: 0 s (0:0), 10 warmups, 10 iterations, in microseconds, completed 2023.9.7

Resources
---------
* [Magnum Github](https://github.com/AugustNagro/magnum)