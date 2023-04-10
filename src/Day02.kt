fun main() {
    val day02 = Day02()

    day02.solve()
}

class Day02 : DayPuzzle<Int>("02", 150, 900) {

    override fun part1(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        val commands = input.map { it.split(" ") }

        for ((direction, amountStr) in commands) {
            val amount = amountStr.toInt()
            when (direction) {
                "up" -> depth -= amount
                "down" -> depth += amount
                "forward" -> horizontal += amount
            }

        }
        return horizontal * depth
    }

    override fun part2(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        var aim = 0

        val commands = input.map { it.split(" ") }

        for ((direction, amountStr) in commands) {
            val amount = amountStr.toInt()
            when (direction) {
                "up" -> aim -= amount
                "down" -> aim += amount
                "forward" -> {
                    horizontal += amount
                    depth += aim * amount
                }
            }

        }
        return horizontal * depth
    }
}