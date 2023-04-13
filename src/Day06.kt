fun main() {
    val day06 = Day06()

    day06.solve()
}

class Day06 : DayPuzzle<Long>("06", 5934, 26984457539) {
    override fun part1(input: List<String>): Long {
        return simulateLanternfishGrowth(80, toFishes(input))
    }

    override fun part2(input: List<String>): Long {
        return simulateLanternfishGrowth(256, toFishes(input))
    }
}

fun toFishes(input: List<String>): List<Int> {
    return input.first().split(",").map { it.toInt() }
}

fun simulateLanternfishGrowth(days: Int, initialState: List<Int>): Long {
    // Create a LongArray of size 9 to store the frequency of lanternfish for each
    // timer value (0-8)
    val timerFrequencies = LongArray(9)

    // Group the initial state by timer value and set the frequency of each
    // timer value in the timerFrequencies array
    initialState.groupBy { it }.forEach { (timer, fish) ->
        timerFrequencies[timer] = fish.size.toLong()
    }

    // Repeat the following block for each day
    repeat(days) {
        // Store the number of lanternfish that will create new
        // lanternfish (those with a timer value of 0)
        val newFish = timerFrequencies[0]
        // Update the timer frequencies by shifting all values one position to the left
        for (i in 0..7) {
            timerFrequencies[i] = timerFrequencies[i + 1]
        }
        // Set the frequency of timer value 8 to the number of new lanternfish
        timerFrequencies[8] = newFish
        // Add the number of new lanternfish to the frequency of timer value 6
        timerFrequencies[6] += newFish
    }

    // Return the sum of all timer frequencies, which is the total number of lanternfish
    return timerFrequencies.sum()
}


