fun main() {
  fun nextElt(list: List<Int>): Int {
    val helperList = mutableListOf(list)
    
    while (!helperList.last().all { it == 0 }) {
      val last = helperList.last()
      
      helperList.add(last.subList(0, last.size - 1).mapIndexed { i, elt ->
        last[i + 1] - elt
      })
    }
    
    var nextItem = helperList.first().last()
    
    for (ls in helperList.subList(1, helperList.size)) {
      nextItem += ls.last()
    }
    
    return nextItem
  }
  
  fun previousElt(list: List<Int>): Int {
    val helperList = mutableListOf(list)
    
    while (!helperList.last().all { it == 0 }) {
      val last = helperList.last()
      
      helperList.add(last.subList(0, last.size - 1).mapIndexed { i, elt ->
        last[i + 1] - elt
      })
    }
    
    var previous = 0
    
    for (ls in helperList.reversed().subList(1, helperList.size)) {
      previous = -previous + ls.first()
    }
    
    return previous
  }
  
  fun part1(input: List<String>) = input.map {
    it.split(' ').map { value -> value.trim().toInt() }
  }.sumOf { nextElt(it) }
  
  fun part2(input: List<String>) = input.map {
    it.split(' ').map { value -> value.trim().toInt() }
  }.sumOf { previousElt(it) }

// test if implementation meets criteria from the description, like:
  val testInput = readInput("Day09_test")
  check(part1(testInput) == 114)
  check(part2(testInput) == 2)
  
  val input = readInput("Day09")
  part1(input).println()
  part2(input).println()
}
