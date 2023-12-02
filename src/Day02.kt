fun main() {
  data class Game(val id: Int, val turns: List<List<Pair<Int, Int>>>)
  
  fun colorToInt(str: String) = when (str.lowercase()) {
    "red" -> 0
    "green" -> 1
    "blue" -> 2
    else -> -1
  }
  
  fun part1(input: List<String>): Int {
    val games = input.map {
      val parts = it.split(':')
      val id = parts[0].split(' ')[1].toInt()
      val turns = parts[1].split(';').map { turn ->
        turn.split(',').map { ball ->
          val (num, color) = ball.trim().split(' ')
          Pair(num.toInt(), colorToInt(color))
        }
      }
      
      Game(id, turns)
    }
    
    return games.sumOf { game ->
      val simplified = game.turns.map {
        val red = it.sumOf { ball -> if (ball.second == 0) ball.first else 0 }
        val green = it.sumOf { ball -> if (ball.second == 1) ball.first else 0 }
        val blue = it.sumOf { ball -> if (ball.second == 2) ball.first else 0 }
        
        listOf(Pair(red, 0), Pair(green, 1), Pair(blue, 2))
      }
      
      val allEntries = simplified.flatten()
      
      val maxRed = allEntries.filter { entry -> entry.second == 0 }.maxOf { entry -> entry.first }
      val maxGreen = allEntries.filter { entry -> entry.second == 1 }.maxOf { entry -> entry.first }
      val maxBlue = allEntries.filter { entry -> entry.second == 2 }.maxOf { entry -> entry.first }
      
      if (maxRed <= 12 && maxGreen <= 13 && maxBlue <= 14) {
        game.id
      } else {
        0
      }
    }
    
  }
  
  fun part2(input: List<String>): Int {
    val games = input.map {
      val parts = it.split(':')
      val id = parts[0].split(' ')[1].toInt()
      val turns = parts[1].split(';').map { turn ->
        turn.split(',').map { ball ->
          val (num, color) = ball.trim().split(' ')
          Pair(num.toInt(), colorToInt(color))
        }
      }
      
      Game(id, turns)
    }
    
    return games.sumOf { game ->
      val simplified = game.turns.map {
        val red = it.sumOf { ball -> if (ball.second == 0) ball.first else 0 }
        val green = it.sumOf { ball -> if (ball.second == 1) ball.first else 0 }
        val blue = it.sumOf { ball -> if (ball.second == 2) ball.first else 0 }
        
        listOf(Pair(red, 0), Pair(green, 1), Pair(blue, 2))
      }
      
      val allEntries = simplified.flatten()
      
      val maxRed = allEntries.filter { entry -> entry.second == 0 }.maxOf { entry -> entry.first }
      val maxGreen = allEntries.filter { entry -> entry.second == 1 }.maxOf { entry -> entry.first }
      val maxBlue = allEntries.filter { entry -> entry.second == 2 }.maxOf { entry -> entry.first }
      
      maxRed * maxGreen * maxBlue
    }
  }
  
  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day02_test")
  check(part1(testInput) == 8)
  
  val input = readInput("Day02")
  part1(input).println()
  part2(input).println()
}
