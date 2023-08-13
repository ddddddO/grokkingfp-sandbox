object TipCalculator {
  def tipPercentage(names: List[String]): Int = {
    if (names.size > 5) 20
    else if (names.size > 0) 10
    else 0
  }
}

// scala> TipCalculator.tipPercentage(List.empty)
// res0: Int = 0

// scala> val smallGroup = List("Alice", "Bob", "Charlie")
// smallGroup: List[String] = List(Alice, Bob, Charlie)

// scala> TipCalculator.tipPercentage(smallGroup)
// res1: Int = 10

// scala> 