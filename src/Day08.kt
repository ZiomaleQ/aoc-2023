import java.math.BigInteger

fun main() {
  data class Path(val left: String, val right: String)
  
  fun part1(input: List<String>): Int {
    val initialInstructions = input[0]
    
    val pathMap = input.subList(2, input.size).associate {
      val parts = it.split('=')
      val path = parts[0].trim()
      
      val options = parts[1].trim().removeSurrounding(prefix = "(", suffix = ")").split(',')
      
      Pair(path, Path(options[0].trim(), options[1].trim()))
    }
    
    var start = "AAA"
    
    val instructions = initialInstructions.toCharArray().toMutableList()
    var count = 0
    
    while (start != "ZZZ") {
      if (instructions.isEmpty()) {
        instructions.addAll(initialInstructions.toCharArray().toList())
      }
      
      val current = instructions.removeFirst()
      
      start = if (current == 'L') pathMap[start]!!.left else pathMap[start]!!.right
      count++
    }
    
    return count
  }
  
  fun part2(input: List<String>): BigInteger? {
    val initialInstructions = input[0]
    
    val pathMap = input.subList(2, input.size).associate {
      val parts = it.split('=')
      val path = parts[0].trim()
      
      val options = parts[1].trim().removeSurrounding(prefix = "(", suffix = ")").split(',')
      
      Pair(path, Path(options[0].trim(), options[1].trim()))
    }
    
    val instructions = initialInstructions.toCharArray().toMutableList()
    
    val steps = pathMap.keys.filter { it.endsWith('A') }.map {
      var start = it
      var count = 0L
      while (true) {
        if (instructions.isEmpty()) {
          instructions.addAll(initialInstructions.toCharArray().toList())
        }
        
        val current = instructions.removeFirst()
        
        start = if (current == 'L') pathMap[start]!!.left else pathMap[start]!!.right
        
        count++
        
        if (start.endsWith('Z')) break
        
      }
      
      count
    }
    return steps.map { BigInteger.valueOf(it) }.reduce { acc, i -> acc * i / acc.gcd(i) }
  }
  
  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day08_test")
  check(part1(testInput) == 2)
  
  val input = readInput("Day08")
  part1(input).println()
  part2(input).println()
}
