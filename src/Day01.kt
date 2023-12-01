fun main() {
  fun part1(input: List<String>): Int {
    return input.sumOf { line -> line.filter { it.isDigit() }.let { it[0].digitToInt() * 10 + it.last().digitToInt() } }
  }
  
  val options = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
  val reversedOptions = options.map { it.reversed() }
  
  val regexFirst = Regex("\\d|" + options.joinToString("|"))
  val regexLast = Regex("\\d|" + reversedOptions.joinToString("|"))
  
  fun part2(input: List<String>): Int {
    var sum = 0
    for (line in input) {
      val first =
        regexFirst.find(line)!!.value.let { if (it[0].isDigit()) it[0].digitToInt() else options.indexOf(it) + 1 }
      val last =
        regexLast.find(line.reversed())!!.value.let {
          if (it[0].isDigit()) it[0].digitToInt() else reversedOptions.indexOf(
            it
          ) + 1
        }
      sum += first * 10 + last
    }
   return sum
  }

// test if implementation meets criteria from the description, like:
  val testInput = readInput("Day01_test")
  check(part1(testInput) == 142)
  
  val input = readInput("Day01")
  part1(input).println()
  part2(input).println()
}
