package utils

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    operator fun minus(other: Point): Point {
        return Point(x - other.x, y - other.y)
    }

    operator fun times(multiplier: Int): Point {
        return Point(x * multiplier, y * multiplier)
    }

    override fun toString(): String {
        return "($x, $y)"
    }
}
