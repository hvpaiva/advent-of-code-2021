fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.toInt() }
            .windowed(2)
            .count { (first, second) -> first < second }
    }

    fun part2(input: List<String>): Int {
        return input
            .asSequence()
            .map { it.toInt() }
            .windowed(4)
            .count { it[0] < it[3] }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    validate(7, part1(testInput))
    validate(5, part2(testInput))

    val input = readInput("Day01")

    println("part1: ${part1(input)}")
    println("part2: ${part2(input)}")
}
