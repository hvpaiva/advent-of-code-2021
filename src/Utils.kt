import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun <T> validate(expected: T?, value: T?, prefixSuccessMessage: () -> String = { "" }) {
    if (expected == value)
        println("${prefixSuccessMessage()} $value")
    else
        println("Failed. Expecting $expected but was $value")
}

fun puzzleRunner(
    day: String,
    part1: (List<String>) -> Any?,
    part1Expected: Any?,
    part2: (List<String>) -> Any?,
    part2Expected: Any?,
    enablePart2: Boolean = false
) {
    val testInput = readInput("${day}_test")
    validate(part1Expected, part1(testInput)) { "Test part1:" }
    if (enablePart2)
        validate(part2Expected, part2(testInput)) { "Test part2:" }

    val input = readInput(day)

    println("part1: ${part1(input)}")
    if (enablePart2)
        println("part2: ${part2(input)}")
}