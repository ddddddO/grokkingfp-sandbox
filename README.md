# grokkingfp-sandbox
「なっとく！関数型プログラミング」を読んで試したコード置き場

- [本家のサンプルコード](https://github.com/miciek/grokkingfp-examples)
  - local: ~/github.com/ddddddO/grokkingfp-examples

- 純粋関数（P270）
  - 戻り値は常に一つだけ
  - 引数だけを使う
  - 既存の値を変更しない


## local実行例

```console
~/github.com/ddddddO/grokkingfp-sandbox
12:56:10 > sbt console
[info] welcome to sbt 1.9.3 (Oracle Corporation Java 17.0.8)
[info] loading project definition from /home/ochi/github.com/ddddddO/grokkingfp-sandbox/project
[info] loading settings for project root from build.sbt ...
[info] set current project to grokkingfp-sandbox (in build file:/home/ochi/github.com/ddddddO/grokkingfp-sandbox/)
[info] compiling 1 Scala source to /home/ochi/github.com/ddddddO/grokkingfp-sandbox/target/scala-3.3.0/classes ...
Welcome to Scala 3.3.0 (17.0.8, Java Java HotSpot(TM) 64-Bit Server VM).
Type in expressions for evaluation. Or try :help.
                                                                                                                                                        
scala> :load hello.scala
// defined object aaa
                                                                                                                                                        
scala> print(aaa.hello("world"))
Hello, world!!~

scala> 9 + 3
val res0: Int = 12
          
scala> 
```