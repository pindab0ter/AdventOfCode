package nl.pindab0ter.lib

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
}
