import kotlin.math.abs

fun main() {
    val day05 = Day05()

    day05.solve()
}

class Day05 : DayPuzzle<Int>("05", 5, 12) {
    override fun part1(input: List<String>): Int {
        val lines = input.map { Line.fromLineString(it) }

        val filteredLines = lines.filter { it.isHorizontalOrVertical() }

        val diagram = Diagram(filteredLines)

        return diagram.howManyIntersections(2)
    }

    override fun part2(input: List<String>): Int {
        val lines = input.map { Line.fromLineString(it) }

        val diagram = Diagram(lines)

        return diagram.howManyIntersections(2)
    }
}

data class Point(val x: Int, val y: Int) {

    override fun toString(): String {
        return "($x, $y)"
    }
}

data class Line(val start: Point, val end: Point) {
    fun isHorizontal(): Boolean = start.y == end.y
    fun isVertical(): Boolean = start.x == end.x
    fun isHorizontalOrVertical(): Boolean = isHorizontal() || isVertical()
    override fun toString(): String {
        return "$start -> $end"
    }

    fun asPoints(): List<Point> {
        val points = mutableListOf<Point>()

        // Calculate the differences in x and y coordinates
        val deltaX = abs(end.x - start.x)
        val deltaY = abs(end.y - start.y)

        // Determine the direction of the line for x and y coordinates
        val stepX = if (start.x < end.x) 1 else -1
        val stepY = if (start.y < end.y) 1 else -1

        // Initialize error value
        var error = deltaX - deltaY
        var currentPoint = start

        // Computes the points of a line between two given points using Bresenham's line algorithm.
        while (true) {
            points.add(currentPoint)

            // If the current point is the end point, break the loop
            if (currentPoint == end) break

            // Calculate the double of the error value
            val doubleError = 2 * error

            // Update x coordinate and error if necessary
            if (doubleError > -deltaY) {
                error -= deltaY
                currentPoint = currentPoint.copy(x = currentPoint.x + stepX)
            }

            // Update y coordinate and error if necessary
            if (doubleError < deltaX) {
                error += deltaX
                currentPoint = currentPoint.copy(y = currentPoint.y + stepY)
            }
        }

        return points
    }

    companion object {
        fun fromLineString(line: String): Line {
            val points = line.split(" -> ")
            val start = points[0].split(",").map { it.toInt() }
            val end = points[1].split(",").map { it.toInt() }

            return Line(Point(start[0], start[1]), Point(end[0], end[1]))
        }
    }

}

class Diagram(lines: List<Line>) {
    private var grid: Array<Array<GridMark>>

    init {
        val maxX = lines.maxOf { it.end.x }
        val maxY = lines.maxOf { it.end.y }
        val max = maxOf(maxX, maxY)

        grid = Array(max + 1) { Array(max + 1) { GridMark() } }

        lines.forEach(::markInGrid)
    }

    fun show() {
        grid.forEach { row ->
            row.forEach { print(it) }
            println()
        }
    }

    private fun markInGrid(line: Line) {
        line.asPoints().forEach(::markPoint)
    }

    private fun markPoint(point: Point) {
        grid[point.y][point.x].increment()
    }

    fun howManyIntersections(atLeast: Int): Int {
        return grid.sumOf { row -> row.count { it.count >= atLeast } }
    }

    data class GridMark(var mark: String = ".", var count: Int = 0) {
        fun increment() {
            count++
            mark = count.toString()
        }

        override fun toString(): String {
            return mark
        }

    }

}