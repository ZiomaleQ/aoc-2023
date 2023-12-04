import kotlin.math.pow

fun main() {
  data class Card(val numbers: List<Int>, val winningNumbers: List<Int>)
  
  fun part1(input: List<String>): Int {
    val cards = input.map {
      val actualData = it.split(':')[1]
      val parts = actualData.split('|')
      
      Card(parts[0].split(' ').filter { entry -> entry.isNotEmpty() }.map { value -> value.trim().toInt() },
        parts[1].split(' ').filter { entry -> entry.isNotEmpty() }.map { value -> value.trim().toInt() })
    }
    
    var sum = 0
    
    for (card in cards) {
      var exp = 0
      
      for (num in card.numbers) {
        if (num in card.winningNumbers) {
          exp++
        }
      }
      
      sum += 2.0.pow(exp - 1.0).toInt()
    }
    
    return sum
  }
  
  fun part2(input: List<String>): Int {
    val cards = input.map {
      val actualData = it.split(':')[1]
      val parts = actualData.split('|')
      
      Card(parts[0].split(' ').filter { entry -> entry.isNotEmpty() }.map { value -> value.trim().toInt() },
        parts[1].split(' ').filter { entry -> entry.isNotEmpty() }.map { value -> value.trim().toInt() })
    }
    
    val copyMap = mutableMapOf<Int, Int>()
    
    for ((idx, card) in cards.withIndex()) {
      var exp = 0
      
      val copies = copyMap[idx] ?: 0
      
      for (num in card.numbers) {
        if (num in card.winningNumbers) {
          exp++
        }
      }
      
      if (exp == 0) {
        continue
      }
      
      for (i in (idx + 1)..<(idx + exp + 1)) {
        copyMap.putIfAbsent(i, 0)
        copyMap[i] = copyMap[i]!! + 1 + copies
      }
    }
    
    
    return cards.size + copyMap.values.sum()
  }
  
  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day04_test")
  check(part1(testInput) == 13)
  
  val input = readInput("Day04")
  part1(input).println()
  part2(input).println()
}
