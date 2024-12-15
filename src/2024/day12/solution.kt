package day12

import utils.IO
import utils.Point

enum class Direction(val direction: Point) {
    LEFT(Point(0, -1)),
    TOP(Point(-1, 0)),
    RIGHT(Point(0, 1)),
    BOTTOM(Point(1, 0)),
    TOP_LEFT(Point(-1, -1)),
    TOP_RIGHT(Point(-1, 1)),
    BOTTOM_RIGHT(Point(1, 1)),
    BOTTOM_LEFT(Point(1, -1))
}

data class Area(val points: Set<Point>, val area: Int, val perimeter: Int, val fieldType: String, val wallsCount: Int)

data class SingleCornerCheck(val required: Set<Direction>, val forbidden: Set<Direction>)

data class Corner(val inner: SingleCornerCheck, val outer: SingleCornerCheck)

class Day12 {
    companion object {
        private val STRAIGHT_DIRECTIONS = listOf(Direction.TOP, Direction.RIGHT, Direction.BOTTOM, Direction.LEFT)

        private val CORNERS = listOf(
            // Top left
            Corner(
                inner = SingleCornerCheck(
                    required = setOf(Direction.TOP, Direction.LEFT),
                    forbidden = setOf(Direction.TOP_LEFT)
                ), outer = SingleCornerCheck(
                    required = emptySet(), forbidden = setOf(
                        Direction.TOP, Direction.LEFT
                    )
                )
            ),
            // Top right
            Corner(
                inner = SingleCornerCheck(
                    required = setOf(Direction.TOP, Direction.RIGHT),
                    forbidden = setOf(Direction.TOP_RIGHT)
                ), outer = SingleCornerCheck(
                    required = emptySet(), forbidden = setOf(
                        Direction.TOP, Direction.RIGHT
                    )
                )
            ),
            // Bottom right
            Corner(
                inner = SingleCornerCheck(
                    required = setOf(Direction.BOTTOM, Direction.RIGHT),
                    forbidden = setOf(Direction.BOTTOM_RIGHT)
                ),
                outer = SingleCornerCheck(
                    required = emptySet(), forbidden = setOf(
                        Direction.BOTTOM, Direction.RIGHT
                    )
                )
            ),
            // Bottom left
            Corner(
                inner = SingleCornerCheck(
                    required = setOf(Direction.BOTTOM, Direction.LEFT),
                    forbidden = setOf(Direction.BOTTOM_LEFT)
                ),
                outer = SingleCornerCheck(
                    required = emptySet(), forbidden = setOf(
                        Direction.BOTTOM, Direction.LEFT
                    )
                )
            )
        )

        fun getTypeAtDirection(
            direction: Point,
            point: Point,
            fieldType: String,
            farm: List<List<String>>,
            returnRawDirection: Boolean = false
        ): Point? {
            val newPoint = point + direction
            val field = farm.elementAtOrNull(newPoint.x)?.elementAtOrNull(newPoint.y)

            if (field == null || field != fieldType) return null

            if (returnRawDirection) return direction

            return newPoint
        }

        fun getCornersCountAtPoint(point: Point, fieldType: String, farm: List<List<String>>): Int {
            val sameTypeFields =
                Direction.entries.mapNotNull { getTypeAtDirection(it.direction, point, fieldType, farm, true) }

            val cornersCount = CORNERS.filter { (inner, outer) ->
                listOf(inner, outer).any { (required, forbidden) ->
                    val areRequiredConditionsMet = required.all { sameTypeFields.contains(it.direction) }
                    val areForbiddenConditionsMet = forbidden.all { !sameTypeFields.contains(it.direction) }

                    areRequiredConditionsMet && areForbiddenConditionsMet
                }
            }.size

            return cornersCount
        }

        fun traverseAreaFromPoint(startPoint: Point, farm: List<List<String>>, fieldType: String): Area {
            val points = mutableSetOf<Point>()
            var area = 0
            var perimeter = 0

            fun traverseArea(point: Point) {
                if (points.contains(point)) return

                val sameTypeFields =
                    STRAIGHT_DIRECTIONS.mapNotNull { getTypeAtDirection(it.direction, point, fieldType, farm) }

                area++
                perimeter += 4 - sameTypeFields.size
                points.add(point)

                sameTypeFields.forEach { traverseArea(it) }
            }

            traverseArea(startPoint)

            var cornersCount = points.sumOf { getCornersCountAtPoint(it, fieldType, farm) }

            return Area(
                points = points,
                area = area,
                perimeter = perimeter,
                fieldType = fieldType,
                // amount of walls will always be equal to amount of corners
                wallsCount = cornersCount
            )
        }

        fun solve() {
            val farm =
                IO.readFileLines("day12/smallInput.txt").map { it.split("").filterNot { char -> char.isEmpty() } }

            val notVisitedFarmPoints = farm.flatMapIndexed { rowIndex, row ->
                row.mapIndexed { colIndex, _ -> Point(rowIndex, colIndex) }
            }.toMutableSet()

            val allAreas = mutableListOf<Area>()

            while (notVisitedFarmPoints.isNotEmpty()) {
                val startPoint = notVisitedFarmPoints.first()
                val fieldType = farm.elementAt(startPoint.x).elementAt(startPoint.y)

                val area = traverseAreaFromPoint(startPoint, farm, fieldType)
                allAreas.add(area)

                notVisitedFarmPoints.removeAll(area.points)
            }

            val fullPrice = allAreas.sumOf { it.area * it.perimeter }

            println("Day 12, part 1: $fullPrice")

            val discountedPrice = allAreas.sumOf { it.area * it.wallsCount }

            println("Day 12, part 2: $discountedPrice")
        }
    }
}

fun main() {
    Day12.solve()
}
