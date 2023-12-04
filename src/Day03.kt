import java.util.*
import java.util.regex.Pattern

fun main() {
  fun part1(input: List<String>): Int {
    val numbers = mutableListOf<Pair<Int, IntRange>>()
    
    for ((idx, line) in input.withIndex()) {
      for (result in Scanner(line).findAll(Pattern.compile("\\d+"))) {
        numbers.add(Pair(idx, result.start()..<result.end()))
      }
    }
    
    var sum = 0
    
    for ((y, xRange) in numbers) {
      val checkList = mutableListOf<Pair<Int, Int>>()
      
      //TOP IS FINE
      if (y > 0) {
        for (x in xRange) {
          checkList.add(Pair(y - 1, x))
        }
      }
      
      //BOTTOM IS FINE
      if (y < input.size - 1) {
        for (x in xRange) {
          checkList.add(Pair(y + 1, x))
        }
      }
      
      if (xRange.first > 0) {
        if (y > 0) {
          checkList.add(Pair(y - 1, xRange.first - 1))
        }
        
        checkList.add(Pair(y, xRange.first - 1))
        
        if (y < input.size - 1) {
          checkList.add(Pair(y + 1, xRange.first - 1))
        }
      }
      
      if (xRange.last < input[0].length - 1) {
        if (y > 0) {
          checkList.add(Pair(y - 1, xRange.last + 1))
        }
        
        checkList.add(Pair(y, xRange.last + 1))
        
        if (y < input.size - 1) {
          checkList.add(Pair(y + 1, xRange.last + 1))
        }
      }
      
      if (checkList.any { input[it.first][it.second] != '.' && !input[it.first][it.second].isDigit() }) {
        sum += input[y].substring(xRange).toInt()
      }
    }
    
    return sum
  }
  
  fun part2(input: List<String>): Int {
    val numbers = mutableListOf<Pair<Int, IntRange>>()
    val gearMap = mutableMapOf<Pair<Int, Int>, MutableList<Pair<Int, IntRange>>>()
    
    for ((idx, line) in input.withIndex()) {
      for (result in Scanner(line).findAll(Pattern.compile("\\d+"))) {
        numbers.add(Pair(idx, result.start()..<result.end()))
      }
    }
    
    for ((y, xRange) in numbers) {
      val checkList = mutableListOf<Pair<Int, Int>>()
      
      //TOP IS FINE
      if (y > 0) {
        for (x in xRange) {
          checkList.add(Pair(y - 1, x))
        }
      }
      
      //BOTTOM IS FINE
      if (y < input.size - 1) {
        for (x in xRange) {
          checkList.add(Pair(y + 1, x))
        }
      }
      
      if (xRange.first > 0) {
        if (y > 0) {
          checkList.add(Pair(y - 1, xRange.first - 1))
        }
        
        checkList.add(Pair(y, xRange.first - 1))
        
        if (y < input.size - 1) {
          checkList.add(Pair(y + 1, xRange.first - 1))
        }
      }
      
      if (xRange.last < input[0].length - 1) {
        if (y > 0) {
          checkList.add(Pair(y - 1, xRange.last + 1))
        }
        
        checkList.add(Pair(y, xRange.last + 1))
        
        if (y < input.size - 1) {
          checkList.add(Pair(y + 1, xRange.last + 1))
        }
      }
      
      for (mapPos in checkList) {
        if (input[mapPos.first][mapPos.second] == '*') {
          gearMap.putIfAbsent(mapPos, mutableListOf())
          
          gearMap[mapPos]!!.add(Pair(y, xRange))
        }
      }
    }
    
    return gearMap.values.asSequence().filter { it.size == 2 }
      .sumOf { list ->
        input[list[0].first].substring(list[0].second).toInt() * input[list[1].first].substring(list[1].second).toInt()
      }
  }
  
  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day03_test")
  check(part1(testInput) == 4361)
  
  val input = readInput("Day03")
  part1(input).println()
  part2(input).println()
}
