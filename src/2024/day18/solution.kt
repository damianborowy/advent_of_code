package day18

import utils.IO
import utils.Point
import java.util.LinkedList

enum class Direction(val vector: Point) {
    TOP(Point(-1, 0)),
    RIGHT(Point(0, 1)),
    BOTTOM(Point(1, 0)),
    LEFT(Point(0, -1)),
}

data class Node(val score: Int, val position: Point)

class Day18 {
    companion object {
        private const val MAP_SIZE = 70

        private val startPoint = Point(0, 0)
        private val endPoint = Point(MAP_SIZE, MAP_SIZE)
        private var allBytes = listOf<Point>()

        private fun isPointOnMap(point: Point): Boolean {
            val (row, col) = point

            return listOf(row, col).all { it >= 0 && it <= MAP_SIZE }
        }

        private fun bfs(bytesCount: Int): Int? {
            val bytes = allBytes.slice(0..<bytesCount).toSet()
            val visitedPoints = mutableSetOf<Point>()
            val queue = LinkedList<Node>()
            queue.add(Node(0, startPoint))

            while (queue.isNotEmpty()) {
                val (score, position) = queue.poll()

                if (position == endPoint) return score

                Direction.entries.forEach { direction ->
                    val newPoint = position + direction.vector
                    val isByte = bytes.contains(newPoint)
                    val isOutOfMap = !isPointOnMap(newPoint)
                    val isPointAlreadyVisited = visitedPoints.contains(newPoint)

                    if (isByte || isOutOfMap || isPointAlreadyVisited) return@forEach

                    visitedPoints.add(newPoint)
                    queue.offer(Node(score + 1, newPoint))
                }
            }

            return null
        }

        fun solve() {
            allBytes = IO.readFileLines("day18/smallInput.txt")
                .map { it.split(",").map { elem -> elem.toInt() } }
                .map { (x, y) -> Point(y, x) }

            val shortestPath = bfs(1024)

            println("Day 18, part 1: $shortestPath")

            val firstBlockingByteIndex = (0..<allBytes.size).indexOfFirst { bfs(it) == null }
            val (blockingX, blockingY) = allBytes.elementAt(firstBlockingByteIndex - 1)

            println("Day 18, part 2: $blockingY,$blockingX")
        }
    }
}

fun main() {
    Day18.solve()
}
