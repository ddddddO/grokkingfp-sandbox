# grokkingfp-sandbox
「なっとく！関数型プログラミング」を読んで試したコード置き場

- [本家のサンプルコード](https://github.com/miciek/grokkingfp-examples)
  - local: ~/github.com/ddddddO/grokkingfp-examples

```console
$ sbt console
[warn] No sbt.version set in project/build.properties, base directory: /home/ochi/github.com/ddddddO/grokkingfp-sandbox
[info] welcome to sbt 1.9.3 (Oracle Corporation Java 11.0.2)
[info] set current project to grokkingfp-sandbox (in build file:/home/ochi/github.com/ddddddO/grokkingfp-sandbox/)
[info] Starting scala interpreter...
Welcome to Scala 2.12.18 (OpenJDK 64-Bit Server VM, Java 11.0.2).
Type in expressions for evaluation. Or try :help.

scala> :load hello.scala
Loading hello.scala...
hello: (s: String)String

scala> print(hello("world"))
Hello, world!!
scala> 10 + 4
res2: Int = 14

scala>
```