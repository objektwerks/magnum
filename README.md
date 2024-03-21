Magnum
------
>Magnum feature test and performance benchmark against H2 using Scala 3.

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
>OpenJDK Runtime Environment Zulu21.28+85-CA (build 21+35), **Scala 3.3.1**, Apple M1
1. addTodo - 0.0
2. listTodos - 0.0
3. updateTodo - 0.0
>Total time: 0 s (0:0), 10 warmups, 10 iterations, average time in microseconds, completed **2023.9.20**

Resources
---------
* [Magnum Github](https://github.com/AugustNagro/magnum)
* [Scala Days, Seattle, 2023](https://www.youtube.com/watch?v=iKNRS5b1zAY)