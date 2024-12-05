package day5

import utils.IO
import kotlin.math.ceil

class Day5 {
    companion object {
        private fun isProperlyOrdered(pages: List<Int>, rules: Map<Int, Set<Int>>): Boolean {
            pages.forEachIndexed { pageIndex, page ->
                val previousPages = pages.slice(0..<pageIndex)
                val pageRules = rules.getOrDefault(page, listOf())

                previousPages.forEach { previousPage ->
                    val isPreviousPageIncorrect = pageRules.contains(previousPage)

                    if (isPreviousPageIncorrect) return false
                }
            }

            return true
        }

        private fun fixPagesOrder(pages: List<Int>, rules: Map<Int, Set<Int>>): List<Int> {
            var orderedPages = listOf(pages.first())

            pages.slice(1..<pages.size).forEach outerLoop@ { page ->
                (0..pages.size).forEach { newIndex ->
                    val newPages = orderedPages.slice(0..<newIndex) + listOf(page) + orderedPages.slice(newIndex..<orderedPages.size)

                    if (isProperlyOrdered(newPages, rules)) {
                        orderedPages = newPages
                        return@outerLoop
                    }
                }
            }

            return orderedPages
        }

        fun solve() {
            val (rawRules, rawUpdates) = IO.readFileLines("day5/fullInput.txt").joinToString("\n").split("\n\n")
                .map { it.split("\n") }

            val updates = rawUpdates.map { it.split(",").map { page -> page.toInt() } }

            val rules = mutableMapOf<Int, MutableSet<Int>>()
            rawRules.map { it.split("|").map { page -> page.toInt() } }.forEach { (firstPage, secondPage) ->
                val currentRule = rules.getOrDefault(firstPage, mutableSetOf())

                currentRule.add(secondPage)

                rules[firstPage] = currentRule
            }

            val (validUpdates, unorderedUpdates) = updates.partition { isProperlyOrdered(it, rules) }

            val sumOfValidUpdatesMiddlePages = validUpdates
                .sumOf { it.elementAt(ceil((it.size / 2).toDouble()).toInt()) }

            println("Day 4, part 1: $sumOfValidUpdatesMiddlePages")

            val sumOfUnorderedMiddlePages = unorderedUpdates
                .map { fixPagesOrder(it, rules) }
                .sumOf { it.elementAt(ceil((it.size / 2).toDouble()).toInt()) }

            println("Day 4, part 2: $sumOfUnorderedMiddlePages")
        }
    }
}

fun main() {
    Day5.solve()
}