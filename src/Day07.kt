fun main() {
  val validCards = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
  
  data class Card(val value: Char) {
    override fun equals(other: Any?): Boolean {
      if (other !is Card) return false
      
      return value == other.value
    }
    
    override fun hashCode(): Int {
      return value.hashCode()
    }
    
    fun getValue(jokers: Boolean = false): Int {
      if (value.isDigit()) return value.digitToInt() - 1
      
      return when (value) {
        'T' -> 9
        'J' -> if (jokers) 0 else 10
        'Q' -> 11
        'K' -> 12
        'A' -> 13
        else -> error("WTH AOC MOMENT")
      }
    }
  }
  
  data class Deck(val cards: List<Card>, val bid: Int, val jokers: Boolean = false) : Comparable<Deck> {
    fun getTypeNoJokers(): DeckType {
      val groupedCards = cards.groupBy { it.value }.map { it.value.size }
      
      if (groupedCards.size == 1) return DeckType.FiveOfAKind
      if (groupedCards.contains(4)) return DeckType.FourOfAKind
      if (groupedCards.contains(3) && groupedCards.contains(2)) return DeckType.FullHouse
      if (groupedCards.size == 3 && groupedCards.contains(3)) return DeckType.ThreeOfAKind
      if (groupedCards.count { it == 2 } == 2 && groupedCards.contains(1)) return DeckType.TwoPair
      if (groupedCards.size == 4 && groupedCards.contains(2)) return DeckType.OnePair
      
      return DeckType.HighCard
    }
    
    fun getTypeWithJokers(): DeckType {
      val filteredCards = cards.filter { it.value != 'J' }
      val jokersCount = 5 - filteredCards.size
      
      if (jokersCount == 0) return getTypeNoJokers()
      
      val optionsToCheck = mutableListOf(filteredCards)
      val optionsToProcess = mutableListOf<List<Card>>()
      
      for (i in 0..<jokersCount) {
        while (optionsToCheck.isNotEmpty()) {
          val opt = optionsToCheck.removeFirst()
          for (ch in validCards) {
            val list = opt + Card(ch)
            if (list.size == 5) {
              optionsToProcess.add(list)
            } else {
              optionsToCheck.add(list)
            }
          }
        }
      }
      
      return optionsToProcess.map { Deck(it, 0).getType() }.minOf { it }
    }
    
    fun getType() = if (jokers) getTypeWithJokers() else getTypeNoJokers()
    
    override fun compareTo(other: Deck): Int {
      val type = getType()
      val otherType = other.getType()
      
      if (type != otherType) {
        return otherType.ordinal - type.ordinal
      }
      
      for (cardIdx in cards.indices) {
        if (cards[cardIdx] != other.cards[cardIdx]) return cards[cardIdx].getValue(jokers) - other.cards[cardIdx].getValue(jokers)
      }
      
      return 0
    }
    
    override fun toString(): String {
      return "Deck(cards='${cards.joinToString("") { it.value.toString() }}', bid=$bid)"
    }
  }
  
  fun part1(input: List<String>): Int {
    val decks = input.map { it.split(' ').let { data -> Deck(data[0].map { ch -> Card(ch) }, data[1].toInt()) } }
    
    return decks.sorted().mapIndexed { idx, elt -> (idx + 1) * elt.bid }.reduce(Int::plus)
  }
  
  fun part2(input: List<String>): Int {
    val decks = input.map { it.split(' ').let { data -> Deck(data[0].map { ch -> Card(ch) }, data[1].toInt(), true) } }
    
    return decks.sorted().mapIndexed { idx, elt -> (idx + 1) * elt.bid }.reduce(Int::plus)
  }
  
  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day07_test")
  check(part1(testInput) == 6440)
  
  val input = readInput("Day07")
  part1(input).println()
  part2(input).println()
}

enum class DeckType {
  FiveOfAKind, FourOfAKind, FullHouse, ThreeOfAKind, TwoPair, OnePair, HighCard
}
