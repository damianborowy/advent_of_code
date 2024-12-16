package day16

import utils.IO
import utils.Point
import java.util.PriorityQueue

enum class Direction(val vector: Point) {
    LEFT(Point(0, -1)),
    TOP(Point(-1, 0)),
    RIGHT(Point(0, 1)),
    BOTTOM(Point(1, 0)),
}

data class PlayerState(val position: Point, val direction: Direction)

data class Node(val score: Int, val state: PlayerState, val visitedPoints: Set<Point>)

class Day16 {
    companion object {
        fun solve() {
            val map = IO.readFileLines("day16/smallInput.txt").map { it.split("").filterNot { char -> char.isEmpty() } }
            val startPoint = Point(map.size - 2, 1)

            val initialPlayerState = PlayerState(startPoint, Direction.RIGHT)
            val initialNode = Node(0, initialPlayerState, mutableSetOf(startPoint))
            val queue = PriorityQueue<Node> { n1, n2 -> n1.score - n2.score }
            queue.add(initialNode)

            val stateScores = mutableMapOf<PlayerState, Int>(initialPlayerState to 0)
            val topScorePaths = mutableListOf<Set<Point>>()
            var lowestScore = Int.MAX_VALUE

            while (queue.isNotEmpty()) {
                val (score, state, visitedPoints) = queue.poll()
                val (position, currentDirection) = state
                val positionValue = map.elementAt(position.x).elementAt(position.y)

                if (positionValue == "E") {
                    if (score > lowestScore) continue

                    lowestScore = score
                    topScorePaths.add(visitedPoints)

                    continue
                }

                Direction.entries.forEach { direction ->
                    val newPoint = position + direction.vector
                    val valueAtPoint = map.elementAt(newPoint.x).elementAt(newPoint.y)

                    if (valueAtPoint == "#") return@forEach

                    val moveScore = if (direction == currentDirection) 1 else 1001
                    val newScore = stateScores.getOrDefault(state, 0) + moveScore
                    val newState = PlayerState(newPoint, direction)

                    if (newScore > stateScores.getOrDefault(newState, Int.MAX_VALUE)) return@forEach

                    stateScores[newState] = newScore
                    queue.add(Node(newScore, newState, visitedPoints + newPoint))
                }
            }

            val bestPoints = topScorePaths.flatten().toSet()

            println("Day 16, part 1: $lowestScore")
            println("Day 16, part 2: ${bestPoints.size}")
        }
    }
}

fun main() {
    Day16.solve()
}
