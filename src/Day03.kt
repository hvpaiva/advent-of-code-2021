fun main() {
    fun part1(input: List<String>): Int {
        val columns = input[0].indices
        val gammaRate = buildString {
            for (column in columns) {
                val (zeroes, ones) = input.bitCountByColumn(column)
                val mostCommonBit = if (zeroes > ones) '0' else '1'
                append(mostCommonBit)
            }
        }
        val epsilonRate = gammaRate.invertedBinary()

        return gammaRate.toInt(2) * epsilonRate.toInt(2)

    }

    fun part2(input: List<String>): Int {
        fun rating(type: RateType): String {
            val columns = input[0].indices
            var candidates = input
            for (column in columns) {
                val (zeroes, ones) = candidates.bitCountByColumn(column)
                val mostCommonBit = if (zeroes > ones) '0' else '1'
                candidates = candidates.filter {
                    when (type) {
                        RateType.OXYGEN -> it[column] == mostCommonBit
                        RateType.CO2 -> it[column] != mostCommonBit
                    }
                }
                if (candidates.size == 1) break
            }
            return candidates.single()
        }

        return rating(RateType.OXYGEN).toInt(2) * rating(RateType.CO2).toInt(2)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    validate(198, part1(testInput))
    validate(230, part2(testInput))

    val input = readInput("Day03")

    println("part1: ${part1(input)}")
    println("part2: ${part2(input)}")
}


private fun List<String>.bitCountByColumn(column: Int): BitCount {
    var zeroCount = 0
    var oneCount = 0
    for (line in this) {
        if (line[column] == '0') zeroCount++ else oneCount++
    }
    return BitCount(zeroCount, oneCount)
}

private fun String.invertedBinary() = this
    .asIterable()
    .joinToString("") { if (it == '0') "1" else "0" }


data class BitCount(val zeroCount: Int, val oneCount: Int)

enum class RateType {
    OXYGEN, CO2
}