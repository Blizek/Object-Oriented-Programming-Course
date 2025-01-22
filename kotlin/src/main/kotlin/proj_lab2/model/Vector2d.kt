package proj_lab2.model

import kotlin.math.max
import kotlin.math.min

data class Vector2d(private val x: Int, private val y: Int) {
    override fun toString(): String {
        return String.format("%d, %d", x, y)
    }

    fun precedes(other: Vector2d): Boolean = x <= other.x && y <= other.y

    fun follows(other: Vector2d): Boolean = x >= other.x && y >= other.y

    fun add(other: Vector2d): Vector2d = Vector2d(x + other.x, y + other.y)

    operator fun plus(other: Vector2d): Vector2d {
        return Vector2d(x + other.x, y + other.y)
    }

    fun subtract(other: Vector2d): Vector2d = Vector2d(x - other.x, y - other.y)

    operator fun minus (other: Vector2d): Vector2d {
        return Vector2d(x - other.x, y - other.y)
    }

    fun upperRight(other: Vector2d): Vector2d =
        Vector2d(max(x, other.x), max(y, other.y))

    fun lowerLeft(other: Vector2d): Vector2d =
        Vector2d(min(x, other.x), min(y, other.y))

    fun opposite(): Vector2d = Vector2d(-x, -y)

    operator fun compareTo(other: Vector2d): Int{
        if (x <= other.x && y < other.y || x < other.x && y <= other.y) return -1
        if (x >= other.x && y > other.y || x > other.x && y >= other.y) return 1
        return 0
    }

    fun getX(): Int = x

    fun getY(): Int = y

}

fun MapDirection.toUnitVector(): Vector2d = getUnitVector(this)
