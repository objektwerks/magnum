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
>OpenJDK Runtime Environment Zulu22.28+91-CA (build 22+36), **Scala 3.4.1-RC2**, Apple M1
1. addTodo - 4.455
2. listTodos - 1.901
3. updateTodo - 3.648
>Total time: 604 s (0:0), 10 warmups, 10 iterations, average time in microseconds, completed **2024.3.21**

Resources
---------
* [Magnum Github](https://github.com/AugustNagro/magnum)
* [Scala Days, Seattle, 2023](https://www.youtube.com/watch?v=iKNRS5b1zAY)