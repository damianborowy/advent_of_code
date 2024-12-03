package day1

import utils.IO
import kotlin.math.abs

class Day1 {
    companion object {
        fun solveFirstPart() {
            val input = IO.readFileLines("day1/fullInput.txt")
            val pairs = input.map { row -> row.split("\\s+".toRegex()).map { it.toInt() } }
            val left = pairs.map { it[0] }.sorted()
            val right = pairs.map { it[1] }.sorted()

            val distances = List(pairs.size) { index -> abs(left[index] - right[index]) }
            val sumOfDistances = distances.sum()

            println("Day 1, part 1: $sumOfDistances")
        }

        fun solveSecondPart() {
            val input = IO.readFileLines("day1/fullInput.txt")
            val pairs = input.map { row -> row.split("\\s+".toRegex()).map { it.toInt() } }

            val rightOccurrences = pairs.map { it[1] }.groupBy { it }.mapValues { it.value.size }
            val similarities = pairs.map { it[0] }.map { it * (rightOccurrences[it] ?: 0) }
            val sumOfSimilarities = similarities.sum()

            println("Day 1, part 2: $sumOfSimilarities")
        }
    }
}

fun main() {
    Day1.solveFirstPart()
    Day1.solveSecondPart()
}