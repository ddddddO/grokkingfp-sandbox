def rankedWords(score: String => Int, words: List[String]): List[String] = {
  words.sortBy(score)
}

// scala> rankedWords(w => score(w) + bonus(w), List("ada", "rust", "java", "scala", "haskell"))
// val res17: List[String] = List(ada, java, rust, haskell, scala)

def score(word: String): Int = {
  word.count(_ == 's')
}

def bonus(word: String): Int = {
  if (word.contains("c")) 5 else 0
}

