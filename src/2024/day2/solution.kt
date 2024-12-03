package day2

import utils.IO
import kotlin.math.abs

class Day2 {
    companion object {
        enum class Sign {
            NEGATIVE, POSITIVE
        }

        fun solveFirstPart() {
            val input = IO.readFileLines("day2/fullInput.txt")
            val rows = input.map { row -> row.split("\\s+".toRegex()).map { it.toInt() } }

            val validReportsCount = rows.count { row ->
                val direction: Sign = if(row[1] > row[0]) Sign.POSITIVE else Sign.NEGATIVE
                var isValid = true

                for ((index, report) in row.withIndex()) {
                    if (index == 0) continue;

                    val diff = report - row[index - 1]
                    val isCorrectSign = (diff < 0 && direction == Sign.NEGATIVE) || (diff > 0 && direction == Sign.POSITIVE)
                    val isInRange = abs(diff) in 1..3

                    if (!isCorrectSign || !isInRange) {
                        isValid = false
                        break
                    }
                }

                isValid
            }

            println("Day 2, part 1: $validReportsCount")
        }

        fun solveSecondPart() {
            val input = IO.readFileLines("day2/fullInput.txt")
            val rows = input.map { row -> row.split("\\s+".toRegex()).map { it.toInt() } }

            val validReportsCount = rows.count { reportRow ->
                val reportVariants = reportRow.indices.toList().map { index ->
                    val variant = reportRow.toMutableList()

                    variant.removeAt(index)

                    variant.toList()
                }

                val firstMatchingVariant = reportVariants.find { subRow ->
                    val direction: Sign = if(subRow[1] > subRow[0]) Sign.POSITIVE else Sign.NEGATIVE
                    var isValid = true

                    for ((index, report) in subRow.withIndex()) {
                        if (index == 0) continue;

                        val diff = report - subRow[index - 1]
                        val isCorrectSign = (diff < 0 && direction == Sign.NEGATIVE) || (diff > 0 && direction == Sign.POSITIVE)
                        val isInRange = abs(diff) in 1..3

                        if (!isCorrectSign || !isInRange) {
                            isValid = false
                            break
                        }
                    }

                    isValid
                }

                firstMatchingVariant != null
            }

            println("Day 2, part 2: $validReportsCount")
        }
    }
}

fun main() {
    Day2.solveFirstPart()
    Day2.solveSecondPart()
}