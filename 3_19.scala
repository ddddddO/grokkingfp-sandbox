def firstTwo(l: List[String]): List[String] = {
  l.slice(0, 2)
}

// scala> val tmp = List("aa", "bb", "cc")
// val tmp: List[String] = List(aa, bb, cc)
                                                                                                                     
// scala> firstTwo(tmp)
// val res6: List[String] = List(aa, bb)
                                                                                                                     
// scala> tmp
// val res7: List[String] = List(aa, bb, cc)
                                                                                                                     
// scala> 


def lastTwo(l: List[String]): List[String] = {
  l.slice(l.size - 2, l.size)
}

// scala> lastTwo(List("a", "b", "c")) == List("b", "c")
// val res9: Boolean = true


def movedFirstTwoToTheEnd(l: List[String]): List[String] = {
  val firstTwo = l.slice(0, 2)
  val rest = l.slice(2, l.size)
  rest.appendedAll(firstTwo)
}

// scala> movedFirstTwoToTheEnd(List("a", "b", "c")) == List("c", "a", "b")
// val res10: Boolean = true


def insertedBeforeLast(l: List[String], elm: String): List[String] = {
  val before = l.slice(0, l.size - 1)
  before.appended(elm).appended(l.last)
}

// scala> insertedBeforeLast(List("a", "b"), "c") == List("a", "c", "b")
// val res11: Boolean = true