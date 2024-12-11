package day11

import utils.IO
import utils.Point

class Day11 {
    companion object {
        fun solve() {
            val rocks = IO.readFileLines("day11/smallInput.txt")[0].split(" ").map { it.toLong() }

            val stones = (0..<25).fold(rocks) { acc, _ ->
                acc.flatMap { stone ->
                    val rawStone = stone.toString()

                    when {
                        stone == 0L -> listOf(1L)
                        rawStone.length % 2 == 0 -> listOf(
                            rawStone.slice(0..<rawStone.length / 2).toLong(),
                            rawStone.slice(rawStone.length / 2..<rawStone.length).toLong()
                        )
                        else -> listOf(stone * 2024)
                    }
                }
            }

            println("Day 11, part 1: ${stones.size}")
        }
    }
}

fun main() {
    Day11.solve()
}