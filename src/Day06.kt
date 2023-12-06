fun main() {
  
  data class Race(val time: Long, val distance: Long) {
    fun getOptions(): Long {
      var options = 0L
      
      for (i in 0..time) {
        if ((time - i) * i > distance) options++
      }
      
      return options
    }
  }
  
  fun part1(input: List<String>): Long {
    val timeFrames = input[0].substringAfter(':').split(' ').filter { it.isNotBlank() }.map { it.toLong() }
    val distanceFrames = input[1].substringAfter(':').split(' ').filter { it.isNotBlank() }.map { it.toLong() }
    
    return timeFrames.indices.map { Race(timeFrames[it], distanceFrames[it]).getOptions() }.reduce(Long::times)
  }
  
  fun part2(input: List<String>): Long {
    val timeFrame =
      input[0].substringAfter(':').split(' ').filter { it.isNotBlank() }.joinToString(separator = "").toLong()
    val distanceFrame =
      input[1].substringAfter(':').split(' ').filter { it.isNotBlank() }.joinToString(separator = "").toLong()
    
    return Race(timeFrame, distanceFrame).getOptions()
  }
  
  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day06_test")
  check(part1(testInput) == 288L)
  
  val input = readInput("Day06")
  part1(input).println()
  part2(input).println()
}
