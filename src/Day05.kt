fun main() {
  data class Mapping(val out: Long, val `in`: Long, val length: Long) {
    operator fun contains(other: Long): Boolean {
      return other >= `in` && other < (`in` + length)
    }
  }
  
  data class Section(val from: String, val to: String, val mappings: List<Mapping>) {
    fun map(input: Long): Long {
      for (mapping in mappings) {
        if (input in mapping) {
          val offset = input - mapping.`in`
          return mapping.out + offset
        }
      }
      
      return input
    }
  }
  
  fun part1(input: List<String>): Long {
    val rawSections = mutableListOf<MutableList<String>>(mutableListOf())
    
    for (line in input) {
      if (line.isBlank()) {
        rawSections.add(mutableListOf())
        continue
      }
      rawSections.last().add(line)
    }
    
    var seeds = rawSections.removeFirst()[0].substringAfter(':').split(' ').filter { entry -> entry.isNotEmpty() }
      .map { value -> value.trim().toLong() }
    
    val sections = rawSections.map {
      val (from, to) = it[0].substringBefore(' ').replace("-to-", " ").split(' ')
      
      val mappings = it.subList(1, it.size).map { row ->
        val (out, `in`, len) = row.split(' ').filter { entry -> entry.isNotEmpty() }
          .map { value -> value.trim().toLong() }
        Mapping(out, `in`, len)
      }
      
      Section(from, to, mappings)
    }
    
    for (section in sections) {
      seeds = seeds.map(section::map)
    }
    
    return seeds.min()
  }
  
  fun part2(input: List<String>): Long {
    val rawSections = mutableListOf<MutableList<String>>(mutableListOf())
    
    for (line in input) {
      if (line.isBlank()) {
        rawSections.add(mutableListOf())
        continue
      }
      rawSections.last().add(line)
    }
    
    val seeds = rawSections.removeFirst()[0].substringAfter(':').split(' ').filter { entry -> entry.isNotEmpty() }
      .map { value -> value.trim().toLong() }
    
    val sections = rawSections.map {
      val (from, to) = it[0].substringBefore(' ').replace("-to-", " ").split(' ')
      
      val mappings = it.subList(1, it.size).map { row ->
        val (out, `in`, len) = row.split(' ').filter { entry -> entry.isNotEmpty() }
          .map { value -> value.trim().toLong() }
        Mapping(out, `in`, len)
      }
      
      Section(from, to, mappings)
    }
    
    var minValue = Long.MAX_VALUE
    
    for ((seed, offset) in seeds.chunked(2)) {
      for (value in seed..<(seed + offset)) {
        
        var temp = value
        
        for (section in sections) {
          temp = section.map(temp)
        }
        
        minValue = minOf(minValue, temp)
      }
    }
    
    return minValue
  }
  
  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day05_test")
  check(part1(testInput) == 35L)
  check(part2(testInput) == 46L)
  
  val input = readInput("Day05")
  part1(input).println()
  part2(input).println()
}
