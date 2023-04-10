fun main() {
    val day01 = Day01()

    day01.solve()
}

class Day01 : DayPuzzle<Int>("01", 7, 5) {

    override fun part1(input: List<String>): Int {
        return input
            .map { it.toInt() }
            .windowed(2)
            .count { (first, second) -> first < second }
    }

    override fun part2(input: List<String>): Int {
        return input
            .asSequence()
            .map { it.toInt() }
            .windowed(4)
            .count { it[0] < it[3] }
    }
}
