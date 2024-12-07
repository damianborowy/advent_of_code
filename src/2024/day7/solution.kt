package day7

import utils.IO
import java.math.BigInteger

class Day7 {
    companion object {
        private fun generateOperatorsPermutations(operatorsCount: Int, operations: List<String>): Set<List<String>> {
            val permutations = mutableSetOf<List<String>>()

            fun generatePermutation(current: List<String>, remainingLength: Int) {
                if (remainingLength == 0) {
                    permutations.add(current)
                    return
                }

                operations.forEach { generatePermutation(current + it, remainingLength - 1) }
            }

            generatePermutation(listOf(), operatorsCount)

            return permutations
        }

        private fun isValidInstruction(instruction: Pair<BigInteger, List<BigInteger>>, operations: List<String>): Boolean {
            val (sum, operands) = instruction
            val operatorsCount = operands.size - 1
            val operatorsPermutations = generateOperatorsPermutations(operatorsCount, operations)

            operatorsPermutations.forEach { operators ->
                val sumForPermutation = operands.slice(1..<operands.size).foldIndexed(operands.first()) { index, acc, element ->
                    val operator = operators.elementAt(index)

                    when (operator) {
                        "+" -> acc + element
                        "*" -> acc * element
                        "||" -> "$acc$element".toBigInteger()
                        else -> acc
                    }
                }

                if (sumForPermutation == sum) return true
            }

            return false
        }

        fun solve() {
            val instructions = IO.readFileLines("day7/fullInput.txt").map { row ->
                val (sum, operands) = row.split(": ")

                Pair(sum.toBigInteger(), operands.split("\\s+".toRegex()).map { it.toBigInteger() })
            }

            val sumOfCorrectEquationsTwoOperators = instructions
                .filter { isValidInstruction(it, listOf("+", "*")) }
                .sumOf { (sum) -> sum }

            println("Day 7, part 1: $sumOfCorrectEquationsTwoOperators")

            val sumOfCorrectEquationsThreeOperators = instructions
                .filter { isValidInstruction(it, listOf("+", "*", "||")) }
                .sumOf { (sum) -> sum }

            println("Day 7, part 2: $sumOfCorrectEquationsThreeOperators")
        }
    }
}

fun main() {
    Day7.solve()
}