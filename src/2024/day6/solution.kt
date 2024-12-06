package day6

import utils.IO

typealias Position = Pair<Int, Int>

data class GuardData(val position: Position, val symbol: String)

data class MoveData(val guardData: GuardData, val isCollision: Boolean)

class Day6 {
    companion object {
        private val DIRECTIONS = mapOf(
            "^" to Pair(-1, 0),
            ">" to Pair(0, 1),
            "v" to Pair(1, 0),
            "<" to Pair(0, -1)
        )

        private val GUARD_ROTATIONS = mapOf(
            "^" to ">",
            ">" to "v",
            "v" to "<",
            "<" to "^"
        )

        private fun findInitialGuardPosition(grid: List<List<String>>): Position {
            grid.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { colIndex, cell ->
                    if (cell == "^") return Pair(rowIndex, colIndex)
                }
            }

            throw IllegalArgumentException()
        }

        private fun moveGuard(grid: List<List<String>>, guard: GuardData, additionalObstaclePosition: Position?): MoveData {
            val (guardRow, guardCol) = guard.position
            val (rowMove, colMove) = DIRECTIONS.getValue(guard.symbol)
            val nextPosition = Pair(guardRow + rowMove, guardCol + colMove)

            val targetElement = grid.elementAtOrNull(nextPosition.first)?.elementAtOrNull(nextPosition.second) ?: ""

            if (targetElement == "#" || nextPosition == additionalObstaclePosition) {
                return MoveData(GuardData(guard.position, GUARD_ROTATIONS.getValue(guard.symbol)), true)
            }

            return MoveData(GuardData(nextPosition, guard.symbol), false)
        }

        private fun isPositionOnMap(grid: List<List<String>>, position: Position): Boolean {
            val (row, col) = position

            return grid.indices.contains(row) && (0..<grid.elementAt(0).size).contains(col)
        }

        private fun canObstacleAtPositionIntroduceLoop(
            obstaclePosition: Position,
            grid: List<List<String>>,
            initialGuardPosition: Position
        ): Boolean {
            val collisions = mutableSetOf<GuardData>()
            var guardData = GuardData(initialGuardPosition, "^")

            while (isPositionOnMap(grid, guardData.position)) {
                val moveData = moveGuard(grid, guardData, obstaclePosition)

                if (moveData.isCollision) {
                    if (collisions.contains(moveData.guardData)) return true

                    collisions.add(moveData.guardData)
                }

                guardData = moveData.guardData
            }

            return false
        }

        fun solve() {
            val grid = IO.readFileLines("day6/fullInput.txt").map { it.split("").filterNot { char -> char.isEmpty() } }
            val initialGuardPosition = findInitialGuardPosition(grid)
            val visitedPositions = mutableSetOf<Position>()
            var guardData = GuardData(initialGuardPosition, "^")

            while (isPositionOnMap(grid, guardData.position)) {
                visitedPositions.add(guardData.position)

                val moveData = moveGuard(grid, guardData, null)

                guardData = moveData.guardData
            }

            val visitedPositionsCount = visitedPositions.size

            println("Day 6, part 1: $visitedPositionsCount")

            visitedPositions.remove(initialGuardPosition)
            val countOfLoopingObstaclePositions =
                visitedPositions.count { canObstacleAtPositionIntroduceLoop(it, grid, initialGuardPosition) }

            println("Day 6, part 2: $countOfLoopingObstaclePositions")
        }
    }
}

fun main() {
    Day6.solve()
}