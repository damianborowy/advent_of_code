package day3

import utils.IO

class Day3 {
    companion object {
        fun solveFirstPart() {
            val inputRows = IO.readFileLines("day3/fullInput.txt")
            val inputText = inputRows.joinToString("")
            val mulRegex = "mul\\((\\d+),(\\d+)\\)".toRegex()

            val sum = mulRegex.findAll(inputText).map { match ->
                val (mul1, mul2) = match.destructured

                mul1.toInt() * mul2.toInt()
            }.sum()

            println("Day 3, part 1: $sum")
        }

        fun solveSecondPart() {
            val inputRows = IO.readFileLines("day3/fullInput.txt")
            val inputText = inputRows.joinToString("")

            val filterRegex = "don't\\(\\).*?do\\(\\)".toRegex()
            val mulRegex = "mul\\((\\d+),(\\d+)\\)".toRegex()
            val filteredInput = inputText.replace(filterRegex, "")

            val sum = mulRegex.findAll(filteredInput).map { match ->
                val (mul1, mul2) = match.destructured

                mul1.toInt() * mul2.toInt()
            }.sum()

            println("Day 3, part 2: $sum")
        }
    }
}

fun main() {
    Day3.solveFirstPart()
    Day3.solveSecondPart()
}