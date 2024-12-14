package day11

import utils.IO

class Day11 {
    companion object {
        fun calculateStonesCountAfterBlinking(initialStones: List<Long>, blinksCount: Int): Long {
            val stonesByValue = initialStones.groupBy { it }.mapValues { it.value.size.toLong() }

            return (0..<blinksCount).fold(stonesByValue) { acc, _ ->
                acc.entries.fold(mutableMapOf<Long, Long>()) { allStones, (stone, stonesCount) ->
                    val rawStone = stone.toString()

                    val newStones = when {
                        stone == 0L -> listOf(1L)
                        rawStone.length % 2 == 0 -> listOf(
                            rawStone.slice(0..<rawStone.length / 2).toLong(),
                            rawStone.slice(rawStone.length / 2..<rawStone.length).toLong()
                        )
                        else -> listOf(stone * 2024)
                    }

                    newStones.forEach { splitStone ->
                        val splitStonesCount = allStones.getOrDefault(splitStone, 0) + stonesCount
                        allStones[splitStone] = splitStonesCount
                    }

                    return@fold allStones
                }
            }.map { (_, stonesCount) -> stonesCount }.sum()
        }

        fun solve() {
            val initialStones = IO.readFileLines("day11/smallInput.txt")[0].split(" ").map { it.toLong() }

            println("Day 11, part 1: ${calculateStonesCountAfterBlinking(initialStones, 25)}")

            println("Day 11, part 2: ${calculateStonesCountAfterBlinking(initialStones, 75)}")
        }
    }
}

fun main() {
    Day11.solve()
}
