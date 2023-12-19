fun main() {
  fun findStart(input: List<String>): Pair<Int, Int> {
    for ((idy, line) in input.withIndex()) {
      for ((idx, ch) in line.withIndex()) {
        if (ch == 'S') return Pair(idy, idx)
      }
    }
    
    return Pair(-1, -1)
  }
  
  fun isValidMove(start: Pair<Char, Pair<Int, Int>>, next: Pair<Char, Pair<Int, Int>>): Boolean {
    val (startChar, startCords) = start
    val (nextChar, nextCords) = next
    
    if (nextChar == '.' || nextChar == 'S') return false
    
    val directionY = startCords.first - nextCords.first
    val directionX = startCords.second - nextCords.second
    
    //Either top or bottom side
    if (directionY != 0) {
      
      //Bottom
      if (directionY == -1) {
        when (startChar) {
          '|', 'S', '7', 'F' -> return nextChar == '|' || nextChar == 'L' || nextChar == 'J'
          '-', 'L', 'J' -> return false
        }
      }
      
      //Top
      if (directionY == 1) {
        when (startChar) {
          '|', 'S', 'L', 'J' -> return nextChar == '|' || nextChar == '7' || nextChar == 'F'
          '-', '7', 'F' -> return false
        }
      }
    }
    
    if (directionX != 0) {
      
      //Left
      if (directionX == 1) {
        when (startChar) {
          '-', 'S', 'J', '7' -> return nextChar == '-' || nextChar == 'L' || nextChar == 'F'
          '|', 'L', 'F' -> return false
        }
      }
      
      //Right
      if (directionX == -1) {
        when (startChar) {
          '-', 'S', 'F', 'L' -> return nextChar == '-' || nextChar == 'J' || nextChar == '7'
          '|', 'J', '7' -> return false
        }
      }
    }
    
    return false
  }
  
  fun part1(input: List<String>): Int {
    val start = findStart(input).also { if (it.first == -1) error("No input?") }
    
    val listToCheck = mutableListOf(
      Pair(start, start.copy(first = start.first + 1)),
      Pair(start, start.copy(first = start.first - 1)),
      Pair(start, start.copy(second = start.second + 1)),
      Pair(start, start.copy(second = start.second - 1))
    )
    
    val costMap = mutableMapOf(start to 0)
    
    while (listToCheck.isNotEmpty()) {
      val (prev, current) = listToCheck.removeFirst()
      
      val (currentY, currentX) = current
      val (previousY, previousX) = prev
      
      if (costMap[current] != null) continue
      if (currentY < 0 || currentY >= input.size) continue
      if (currentX < 0 || currentX >= input[0].length) continue
      
      val char = input[currentY][currentX]
      
      val isValid = isValidMove(Pair(input[previousY][previousX], prev), Pair(char, (current)))
      
      if (isValid) {
        costMap[current] = costMap[prev]!! + 1
        listToCheck.add(Pair(current, current.copy(first = current.first + 1)))
        listToCheck.add(Pair(current, current.copy(first = current.first - 1)))
        listToCheck.add(Pair(current, current.copy(second = current.second + 1)))
        listToCheck.add(Pair(current, current.copy(second = current.second - 1)))
      }
    }
    
    return costMap.values.max()
  }
  
  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day10_test")
  check(part1(testInput) == 8)
  
  val input = readInput("Day10")
  part1(input).println()
}
