def sum(nums: List[Int]): Int = {
  nums.foldLeft(0)((total, num) => total + num)
}
// scala> sum(List(1,2,3,4,5))
// val res27: Int = 15

def max(nums: List[Int]): Int = {
  nums.foldLeft(Int.MinValue)((max, i) => if (i > max) i else max)
}
// scala> max(List(0,1,2,10,3,4))
// val res28: Int = 10