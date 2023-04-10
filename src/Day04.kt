fun main() {
    val day04 = Day04()

    day04.solve()
}

class Day04 : DayPuzzle<Int>("04", 4512, 1924) {

    override fun part1(input: List<String>): Int {
        val draws = draws(input)
        val bingoBoards = bingoBoard(input)

        return getBingoResults(draws, bingoBoards).first()
    }

    override fun part2(input: List<String>): Int {
        val draws = draws(input)
        val bingoBoards = bingoBoard(input)

        return getBingoResults(draws, bingoBoards).last()
    }
}

fun draws(input: List<String>): List<Int> {
    return input.first().split(",").map { it.toInt() }
}

fun bingoBoard(input: List<String>): List<BingoBoard> {
    return input
        .asSequence()
        // skip the first line, as it contains draws
        .drop(1)
        // split the input into chunks of 6 lines (1 empty and 5 with the board)
        .chunked(6)
        // Remove empty lines
        .map { board -> board.filter { line -> line.isNotBlank() } }
        // Convert to Int, splitting on spaces
        .map { board ->
            board.map { line ->
                line.trim()
                    .split(Regex("\\W+"))
                    .map { it.toInt() }
            }
        }
        .map { BingoBoard.fromCollection(it) }
        .toList()
}

fun getBingoResults(draws: List<Int>, boards: List<BingoBoard>): List<Int> {
    var bingoBoards = boards
    val bingo = mutableListOf<Int>()
    draws.forEach { draw ->
        bingoBoards.forEach { board ->
            board.mark(draw)
            if (board.bingo()) {
                val sumOfUnmarked = board.unmarked().sum()
                bingo.add(sumOfUnmarked * draw)
                bingoBoards = bingoBoards - board
            }
        }
    }
    return bingo
}

data class Field(val value: Int, var marked: Boolean = false) {
    fun mark() {
        marked = true
    }
}

data class BingoBoard(val fields: List<List<Field>>) {

    private val widthIndices = fields[0].indices
    private val heightIndices = fields.indices

    companion object {
        fun fromCollection(collection: List<List<Int>>): BingoBoard {
            return BingoBoard(collection.map { row -> row.map { Field(it) } })
        }
    }

    fun bingo() = rowComplete() || columnComplete()

    private fun rowComplete() = fields.any { row -> row.all { it.marked } }

    private fun columnComplete(): Boolean {
        for (column in widthIndices) {
            var complete = true
            for (row in heightIndices) {
                if (!fields[row][column].marked) {
                    complete = false
                    continue
                }
            }
            if (complete) return true
        }
        return false
    }

    fun mark(number: Int) {
        fields.forEach { row ->
            row.map {
                if (it.value == number) it.mark()
            }
        }
    }

    fun unmarked() = fields.flatten().filterNot { it.marked }.map { it.value }
}