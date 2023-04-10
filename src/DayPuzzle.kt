abstract class DayPuzzle<T>(
    private val day: String,
    private val expectedPart1: T,
    private val expectedPart2: T
) {

    abstract fun part1(input: List<String>): T

    abstract fun part2(input: List<String>): T

    fun solve(onlyTests: Boolean = false, activatePart2: Boolean = true) {
        val testInput = readInput("Day${day}_test")
        validate(expectedPart1, part1(testInput)) { "Test part1:" }
        if (activatePart2)
            validate(expectedPart2, part2(testInput)) { "Test part2:" }

        if (!onlyTests) {
            val input = readInput("Day$day")

            println("part1: ${part1(input)}")
            if (activatePart2)
                println("part2: ${part2(input)}")
        }
    }
}