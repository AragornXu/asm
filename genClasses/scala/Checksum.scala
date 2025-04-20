object Checksum {

  def main(args: Array[String]): Unit = {}

  def checksum[T](array: Array[T]): Int = {

    /** if (T == Int) { if (array.instanceOf[Array[Int]]) var sum = 42 var i = 0 while (i <
      * array.length) { sum += (array: Array[Int])(i).## i += 1 } sum } else { ... }
      */
    var sum = 42
    var i = 0
    while (i < array.length) {
      sum += array(i).##
      i += 1
    }
    sum
  }
}