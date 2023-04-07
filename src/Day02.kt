fun main() {
    fun part1(input: List<String>): Int {
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

    fun part2(input: List<String>): Int {
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

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    validate(150, part1(testInput))
    validate(900, part2(testInput))

    val input = readInput("Day02")

    println("part1: ${part1(input)}")
    println("part2: ${part2(input)}")
}
