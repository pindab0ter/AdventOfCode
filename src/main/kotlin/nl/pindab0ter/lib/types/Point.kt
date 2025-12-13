package nl.pindab0ter.lib.types

import nl.pindab0ter.lib.types.Direction.*
import kotlin.math.abs

/** Represents a point in 2D space. */
data class Point(val x: Long, val y: Long) {
    constructor(x: Int, y: Int) : this(x.toLong(), y.toLong())

    /** Manhattan distance (sum of absolute coordinate differences). */
    fun manhattanDistanceTo(other: Point) = abs(x - other.x) + abs(y - other.y)
    fun manhattanDistanceTo(x: Long, y: Long) = manhattanDistanceTo(Point(x, y))

    fun translate(direction: Direction, distance: Long = 1) = when (direction) {
        NORTH -> toTheNorth(distance)
        EAST -> toTheEast(distance)
        SOUTH -> toTheSouth(distance)
        WEST -> toTheWest(distance)
    }

    fun toTheNorth(distance: Long = 1): Point = Point(x, y - distance)
    fun toTheEast(distance: Long = 1): Point = Point(x + distance, y)
    fun toTheSouth(distance: Long = 1): Point = Point(x, y + distance)
    fun toTheWest(distance: Long = 1): Point = Point(x - distance, y)
}
