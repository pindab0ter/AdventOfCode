package nl.pindab0ter.lib

import nl.pindab0ter.lib.Direction.*
import kotlin.math.abs

/**
 * Represents a 2D coordinate in a [Cartesian](https://simple.wikipedia.org/wiki/Cartesian_coordinate_system) plane.
 */
data class Coordinate(val x: Long, val y: Long) {
    companion object {
        operator fun invoke(x: Int, y: Int) = Coordinate(x.toLong(), y.toLong())
    }

    /**
     * The [Manhattan distance](https://simple.wikipedia.org/wiki/Manhattan_distance) is the shortest distance between two
     * points on a [Cartesian](https://simple.wikipedia.org/wiki/Cartesian_coordinate_system) plane.
     *
     * @return The Manhattan distance between the two [Coordinate]s.
     */
    fun manhattanDistance(to: Coordinate) = abs(x - to.x) + abs(y - to.y)
    fun manhattanDistance(x: Long, y: Long) = manhattanDistance(Coordinate(x, y))

    fun translate(direction: Direction, distance: Long = 1) = when (direction) {
        NORTH -> north(distance)
        EAST -> east(distance)
        SOUTH -> south(distance)
        WEST -> west(distance)
    }

    fun north(distance: Long = 1): Coordinate = Coordinate(x, y - distance)
    fun east(distance: Long = 1): Coordinate = Coordinate(x + distance, y)
    fun south(distance: Long = 1): Coordinate = Coordinate(x, y + distance)
    fun west(distance: Long = 1): Coordinate = Coordinate(x - distance, y)
}
