data class Point2D(val x: Int, val y: Int)

data class State(val point: Point2D, val minutes: Int) {
    fun possibleDirections(): List<State> =
        listOf(
            State(point, minutes + 1),
            State(Point2D(point.x + 1, point.y), minutes + 1),
            State(Point2D(point.x - 1, point.y), minutes + 1),
            State(Point2D(point.x, point.y + 1), minutes + 1),
            State(Point2D(point.x, point.y - 1), minutes + 1),
        )
}

fun <T> arrayDequeOf(vararg elements: T) = ArrayDeque(elements.toList())

class Day24Solver(input: List<List<String>>) {
    private val rows = input.size - 2
    private val columns = input.get(0).size - 2
    private val start = Point2D(0, 1)
    private val end = Point2D(rows + 1, columns)
    private val winds = input.drop(1).dropLast(1).map { it.drop(1).dropLast(1) }

    fun solve(): Pair<Int, Int> {
        val firstTime = travelTime(start, end, 0)
        val backForSnacksTime = travelTime(end, start, firstTime)
        val secondTime = travelTime(start, end, backForSnacksTime)

        return Pair(firstTime, secondTime)
    }

    private fun travelTime(from: Point2D, to: Point2D, initialMinutes: Int): Int {
        val visited = mutableSetOf<State>()
        val stack = arrayDequeOf<State>(State(from, initialMinutes))

        while (stack.isNotEmpty()) {
            val currentState = stack.removeFirst()

            if (currentState.point == to) return currentState.minutes

            currentState.possibleDirections()
                .filter { it.canMove(visited) }
                .forEach {
                    stack.addLast(it)
                    visited.add(State(it.point, it.minutes % (rows * columns)))
                }
        }

        return -1
    }

    private fun State.canMove(visited: MutableSet<State>): Boolean {
        if (point == start || point == end) return true

        if (point.x <= 0 || point.x >= rows + 1 || point.y == 0 || point.y == columns + 1) {
            return false
        }

        return !hasWind() && !visited.contains(State(point, minutes % (rows * columns)))
    }

    private fun State.hasWind(): Boolean {
        val x = point.x - 1
        val y = point.y - 1

        return (
            winds.get((x - minutes).mod(rows)).get(y) == "v" ||
            winds.get((x + minutes).mod(rows)).get(y) == "^" ||
            winds.get(x).get((y - minutes).mod(columns)) == ">" ||
            winds.get(x).get((y + minutes).mod(columns)) == "<" 
        )
    }
}

fun main() {
    val inputString = """"""

    val solver = Day24Solver(inputString.split("\n").map { it.split("").drop(1).dropLast(1) })
    val (part1, part2) = solver.solve()

    println("Part 1: $part1")
    println("Part 2: $part2")
}
