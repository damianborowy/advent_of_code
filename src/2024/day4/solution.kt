package day4

import utils.IO
import java.util.*

class Day4 {
    companion object {
        private val EIGHT_DIRECTIONS: List<List<Pair<Int, Int>>> = listOf(
            listOf(Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(3, 0)),
            listOf(Pair(-3, 0), Pair(-2, 0), Pair(-1, 0), Pair(0, 0)),
            listOf(Pair(0, -3), Pair(0, -2), Pair(0, -1), Pair(0, 0)),
            listOf(Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(0, 3)),
            listOf(Pair(-3, -3), Pair(-2, -2), Pair(-1, -1), Pair(0, 0)),
            listOf(Pair(0, 0), Pair(1, 1), Pair(2, 2), Pair(3, 3)),
            listOf(Pair(-3, 3), Pair(-2, 2), Pair(-1, 1), Pair(0, 0)),
            listOf(Pair(0, 0), Pair(1, -1), Pair(2, -2), Pair(3, -3)),
        )

        private val CROSS_DIRECTIONS: List<List<Pair<Int, Int>>> = listOf(
            listOf(Pair(-1, -1), Pair(0, 0), Pair(1, 1)),
            listOf(Pair(-1, 1), Pair(0, 0), Pair(1, -1)),
        )

        private val PART1_CORRECT_VALUES: Set<String> = setOf("XMAS", "XMAS".reversed())
        private val PART2_CORRECT_VALUES: Set<String> = setOf("MAS", "MAS".reversed())

        fun solveFirstPart() {
            val inputRows = IO.readFileLines("day4/fullInput.txt").map { it.split("") }

            var xmasCount = 0
            inputRows.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { cellIndex, _ ->
                    val possibleDirections = EIGHT_DIRECTIONS.mapNotNull { pairs ->
                        try {
                            pairs.joinToString("") {
                                inputRows.elementAt(it.first + rowIndex).elementAt(it.second + cellIndex)
                            }.uppercase(Locale.getDefault())
                        } catch (_: Exception) {
                            null
                        }
                    }

                    xmasCount += possibleDirections.count { PART1_CORRECT_VALUES.contains(it) }
                }
            }

            xmasCount /= 2

            println("Day 4, part 1: $xmasCount")
        }

        fun solveSecondPart() {
            val inputRows = IO.readFileLines("day4/fullInput.txt").map { it.split("") }

            var xmasCount = 0
            inputRows.forEachIndexed { rowIndex, row ->
                row.forEachIndexed rowIt@ { cellIndex, cellValue ->
                    if (cellValue != "A") return@rowIt

                    val crossDirections = CROSS_DIRECTIONS.mapNotNull { pairs ->
                        try {
                            pairs.joinToString("") {
                                inputRows.elementAt(it.first + rowIndex).elementAt(it.second + cellIndex)
                            }.uppercase(Locale.getDefault())
                        } catch (_: Exception) {
                            null
                        }
                    }

                    val isProperCross = crossDirections.size == 2 && crossDirections.all {
                        PART2_CORRECT_VALUES.contains(it)
                    }

                    if (isProperCross) xmasCount++;
                }
            }

            println("Day 4, part 2: $xmasCount")
        }
    }
}

fun main() {
    Day4.solveFirstPart()
    Day4.solveSecondPart()
}