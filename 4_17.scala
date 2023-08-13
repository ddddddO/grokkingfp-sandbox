def rankedWordsWithContainS(words: List[String]): List[String] = {
  def score(word: String): Int = {
    word.count(_ == 's')
  }
  rankedWords(score, words)
}

// scala> rankedWordsWithContainS(List("rust", "ada")) == List("ada", "rust")
// val res13: Boolean = true

def rankedWords(score: String => Int, words: List[String]): List[String] = {
  words.sortBy(score)
}

