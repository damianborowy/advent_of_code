package day20

import utils.IO
import utils.Point
import java.util.LinkedList

enum class Direction(val vector: Point) {
    LEFT(Point(0, -1)),
    TOP(Point(-1, 0)),
    RIGHT(Point(0, 1)),
    BOTTOM(Point(1, 0)),
}

data class Node(val score: Int, val position: Point, val visitedPointScores: Map<Point, Int>)

data class BfsResult(val score: Int, val lowestPathScores: Map<Point, Int>)

class Day20 {
    companion object {
        fun bfs(startPoint: Point, endPoint: Point, map: List<List<String>>): BfsResult {
            val initialNode = Node(0, startPoint, mutableMapOf(startPoint to 0))
            val visitedPoints = mutableSetOf<Point>()

            val queue = LinkedList<Node>()
            queue.push(initialNode)

            while (queue.isNotEmpty()) {
                val node = queue.poll()
                val (score, position, pathScores) = node

                if (position == endPoint) return BfsResult(score, pathScores)

                Direction.entries.forEach { direction ->
                    val newPoint = position + direction.vector
                    val valueAtPoint = map.elementAt(newPoint.x).elementAt(newPoint.y)
                    val isPointAlreadyVisited = visitedPoints.contains(newPoint)

                    if (valueAtPoint == "#" || isPointAlreadyVisited) return@forEach

                    visitedPoints.add(newPoint)

                    val newPathScores = pathScores.toMutableMap()
                    newPathScores[newPoint] = score + 1

                    queue.offer(Node(score + 1, newPoint, newPathScores))
                }
            }

            return BfsResult(-1, mapOf())
        }

        fun getPossibleWaysToCheatCount(pathScores: Map<Point, Int>, maxDistance: Int, minCheatingBenefit: Int): Int =
            pathScores.entries.sumOf { (point, score) ->
                pathScores.entries.count { (otherPoint, otherScore) ->
                    val distance = point.manhattanDistance(otherPoint)
                    val isReachable = distance <= maxDistance
                    val isWorthCheating = otherScore >= score + distance + minCheatingBenefit

                    return@count isReachable && isWorthCheating
                }
            }

        fun solve() {
            val map = IO.readFileLines("day20/smallInput.txt").map { it.split("").filterNot { char -> char.isEmpty() } }
            var startPoint = Point(-1, -1)
            var endPoint = Point(-1, -1)

            map.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { colIndex, cell ->
                    when (cell) {
                        "S" -> startPoint = Point(rowIndex, colIndex)
                        "E" -> endPoint = Point(rowIndex, colIndex)
                    }
                }
            }

            val (_, pathScores) = bfs(startPoint, endPoint, map)

            println("Day 20, part 1: ${getPossibleWaysToCheatCount(pathScores, 2, 100)}")
            println("Day 20, part 2: ${getPossibleWaysToCheatCount(pathScores, 20, 100)}")
        }
    }
}

fun main() {
    Day20.solve()
}
