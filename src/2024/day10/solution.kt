package day10

import utils.IO
import utils.Point

data class TrailheadData(val reachablePeaks: Set<Point>, val trailsToPeak: Set<List<Point>>)

class Day10 {
    companion object {
        private val DIRECTIONS = listOf(
            Point(-1, 0),
            Point(0, 1),
            Point(1, 0),
            Point(0, -1)
        )

        private fun getTrailheadData(startPoint: Point, map: List<List<Int>>): TrailheadData {
            fun traversePath(trail: List<Point>, position: Point, height: Int): Pair<Set<List<Point>>, Set<Point>> {
                if (height == 9) return setOf(trail) to setOf(position)

                val result = DIRECTIONS.mapNotNull { direction ->
                    val newPoint = position + direction
                    val valueAtPoint = map.elementAtOrNull(newPoint.x)?.elementAtOrNull(newPoint.y)

                    if (valueAtPoint is Int && valueAtPoint == height + 1) {
                        traversePath(trail + newPoint, newPoint, height + 1)
                    } else null
                }

                val combinedTrails = result.flatMap { it.first }.toSet()
                val combinedPeaks = result.flatMap { it.second }.toSet()

                return combinedTrails to combinedPeaks
            }

            val (trailsToPeak, reachablePeaks) = traversePath(listOf(startPoint), startPoint, 0)

            return TrailheadData(trailsToPeak = trailsToPeak, reachablePeaks = reachablePeaks)
        }

        fun solve() {
            val map = IO.readFileLines("day10/smallInput.txt")
                .map { row ->
                    row
                        .split("")
                        .filterNot { char -> char.isEmpty() }
                        .map { elem -> if (elem == ".") -1 else elem.toInt() }
                }

            val trailheads = map.flatMapIndexed { rowIndex, row ->
                row.mapIndexedNotNull { colIndex, cell ->
                    when (cell) {
                        0 -> getTrailheadData(Point(rowIndex, colIndex), map)
                        else -> null
                    }
                }
            }

            val sumOfReachablePeaks = trailheads.sumOf { it.reachablePeaks.size }

            println("Day 10, part 1: $sumOfReachablePeaks")

            val sumOfAvailableTrails = trailheads.sumOf { it.trailsToPeak.size }

            println("Day 10, part 1: $sumOfAvailableTrails")
        }
    }
}

fun main() {
    Day10.solve()
}