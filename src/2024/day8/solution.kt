package day8

import utils.IO

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    operator fun minus(other: Point): Point {
        return Point(x - other.x, y - other.y)
    }

    operator fun times(multiplier: Int): Point {
        return Point(x * multiplier, y * multiplier)
    }
}

class Day8 {
    companion object {
        private fun getCombinationsOfAntennas(antennas: List<Point>): Set<List<Point>> {
            val permutations = mutableSetOf<List<Point>>()

            fun generatePermutation(current: List<Point>, remainingLength: Int) {
                if (remainingLength == 0) {
                    val (first, second) = current

                    if (first == second || permutations.contains(listOf(second, first))) return

                    permutations.add(current)
                    return
                }

                antennas.forEach { generatePermutation(current + it, remainingLength - 1) }
            }

            generatePermutation(listOf(), 2)

            return permutations
        }

        private fun getSingleAntiNodesPoints(
            map: List<List<String>>,
            groupedAntennas: Map<String, List<Point>>
        ): Set<Point> {
            val antiNodes = mutableSetOf<Point>()

            groupedAntennas.forEach { (_, antennas) ->
                val combinations = getCombinationsOfAntennas(antennas)

                combinations.forEach { (first, second) ->
                    val newPoints = setOf(first * 2 - second, second * 2 - first)

                    newPoints.filter { isPointOnMap(map, it) }.forEach { antiNodes.add(it) }
                }
            }

            return antiNodes
        }

        private fun getManyPointsInLine(map: List<List<String>>, first: Point, second: Point): Set<Point> {
            val points = mutableSetOf<Point>()
            var nextPoint = first

            while (isPointOnMap(map, nextPoint)) {
                points.add(nextPoint)
                nextPoint += first - second
            }

            return points
        }

        private fun getMultipleAntiNodesPositions(
            map: List<List<String>>,
            groupedAntennas: Map<String, List<Point>>
        ): Set<Point> {
            val antiNodes = mutableSetOf<Point>()

            groupedAntennas.forEach { (_, antennas) ->
                val combinations = getCombinationsOfAntennas(antennas)

                combinations.forEach { (a, b) ->
                    getManyPointsInLine(map, a, b).forEach { antiNodes.add(it) }
                    getManyPointsInLine(map, b, a).forEach { antiNodes.add(it) }
                }
            }

            return antiNodes
        }

        private fun isPointOnMap(map: List<List<String>>, point: Point): Boolean {
            val (row, col) = point

            return map.indices.contains(row) && (0..<map.elementAt(0).size).contains(col)
        }

        fun solve() {
            val map =
                IO.readFileLines("day8/fullInput.txt").map { it.split("").filterNot { char -> char.isEmpty() } }
            val groupedAntennas = mutableMapOf<String, List<Point>>()

            map.forEachIndexed { rowIndex, row ->
                row.forEachIndexed rowIt@{ colIndex, cell ->
                    if (cell == ".") return@rowIt

                    val group = groupedAntennas.getOrDefault(cell, listOf())
                    groupedAntennas[cell] = group + Point(rowIndex, colIndex)
                }
            }

            val antiNodes = getSingleAntiNodesPoints(map, groupedAntennas)

            println("Day 8, part 1: ${antiNodes.size}")

            val multipleAntiNodes = getMultipleAntiNodesPositions(map, groupedAntennas)

            println("Day 8, part 2: ${multipleAntiNodes.size}")
        }
    }
}

fun main() {
    Day8.solve()
}