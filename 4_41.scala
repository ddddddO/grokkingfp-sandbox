def overNs(n: Int): List[Int] => List[Int] = {
  nums => nums.filter(num => num > n)
}
// scala> val nums = List(5, 1, 2, 4, 0)
// val nums: List[Int] = List(5, 1, 2, 4, 0)

// scala> overNs(4)(nums)
// val res21: List[Int] = List(5)
                                                                                                                     
// scala> overNs(1)(nums)
// val res22: List[Int] = List(5, 2, 4)


def largerThan(n: Int): Int => Boolean = i => i > n
// scala> nums.filter(largerThan(4))
// val res23: List[Int] = List(5)
                                                                                                                     
// scala> nums.filter(largerThan(1))
// val res24: List[Int] = List(5, 2, 4)

