package day9

import utils.Either
import utils.IO

fun <T> MutableList<T>.swap(firstIndex: Int, secondIndex: Int): MutableList<T> = apply {
    val value = this[firstIndex]
    this[firstIndex] = this[secondIndex]
    this[secondIndex] = value
}

typealias MemoryBlock = Either<Int, Char>
typealias FileBlock = Either.Left<Int>
typealias FreeSpace = Either.Right<Char>

class Day9 {
    companion object {
        private fun calculateCheckSum(map: List<MemoryBlock>): ULong {
            val lastNumberIndex = map.indexOfLast { it is FileBlock }
            val finalMap = map.slice(0..lastNumberIndex)

            return finalMap
                .mapIndexedNotNull { index, fileId ->
                    when (fileId) {
                        is FileBlock -> fileId.value.toULong() * index.toULong()
                        else -> null
                    }
                }
                .sum()
        }

        private fun solveFirstPart(map: List<MemoryBlock>) {
            var leftIndex = 0
            var rightIndex = map.size - 1
            val reorderedMap = map.toMutableList()

            while (leftIndex < rightIndex) {
                while (reorderedMap.elementAt(leftIndex) is FileBlock && leftIndex < rightIndex) leftIndex++
                while (reorderedMap.elementAt(rightIndex) is FreeSpace && leftIndex < rightIndex) rightIndex--

                reorderedMap.swap(leftIndex, rightIndex)
            }

            val checkSum = calculateCheckSum(reorderedMap)

            println("Day 9, part 1: $checkSum")
        }

        private fun solveSecondPart(map: List<MemoryBlock>) {
            var rightIndex = map.size - 1
            val reorderedMap = map.toMutableList()

            while (rightIndex > 0) {
                val element = reorderedMap.elementAt(rightIndex--)

                if (element !is FileBlock) continue

                val firstRightIndex = reorderedMap.indexOf(element)
                val blockSize = (rightIndex + 1) - firstRightIndex

                rightIndex -= blockSize

                val firstLeftIndex = reorderedMap.withIndex().indexOfFirst { (index, element) ->
                    index + blockSize < reorderedMap.size && reorderedMap.slice(index..index + blockSize).all { it is FreeSpace }
                }

                if (firstLeftIndex == -1 || firstLeftIndex >= firstRightIndex) continue

                (0..blockSize).forEach { index ->
                    reorderedMap.swap(firstLeftIndex + index, firstRightIndex + index)
                }
            }

            val checkSum = calculateCheckSum(reorderedMap)

            println("Day 9, part 2: $checkSum")
        }

        fun solve() {
            val rawMap = IO.readFileLines("day9/smallInput.txt")[0]
            val map = rawMap.split("").filterNot { char -> char.isEmpty() }
                .flatMapIndexed { index, space ->
                    MutableList(space.toInt()) { _ ->
                        if (index % 2 == 0) FileBlock(index / 2) else FreeSpace('.')
                    }
                }

            solveFirstPart(map)
            solveSecondPart(map)
        }
    }
}

fun main() {
    Day9.solve()
}