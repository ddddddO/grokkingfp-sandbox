def lengths(words: List[String]): List[Int] = {
  words.map(w => w.length)
}
// scala> lengths(List("ada", "rust")) == List(3, 4)
// val res18: Boolean = true

def countS(words: List[String]): List[Int] = {
  words.map(w => w.count(_ == 's'))
}
// scala> countS(List("ada", "rust")) == List(0, 1)
// val res19: Boolean = true