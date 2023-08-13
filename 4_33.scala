def underFiveWords(words: List[String]): List[String] = {
  words.filter(w => w.length < 5)
}
// scala> underFiveWords(List("scala", "rust")) == List("rust")
// val res20: Boolean = true

