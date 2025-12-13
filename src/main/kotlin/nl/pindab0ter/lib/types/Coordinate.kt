package nl.pindab0ter.lib.types

import kotlin.math.abs

/**
 * Represents a 2D coordinate in a [Cartesian coordinate system](https://simple.wikipedia.org/wiki/Cartesian_coordinate_system).
 *
 * Uses a standard coordinate system where:
 * - X-axis runs horizontally (positive values to the right)
 * - Y-axis runs vertically (positive values downward, following typical screen/grid conventions)
 *
 * Common operations include:
 * - Distance calculations (Manhattan distance)
 * - Directional movement (north, south, east, west)
 * - Translation by direction
 *
 * Example:
 * ```
 * val origin = Coordinate(0, 0)
 * val point = Coordinate(3, 4)
 * point.manhattanDistance(origin)  // 7
 * point.north(2)                   // Coordinate(3, 2)
 * ```
 *
 * @property x The horizontal position (column).
 * @property y The vertical position (row).
 */
data class Coordinate(val x: Long, val y: Long) {
    /**
     * Creates a coordinate from [Int] values, converting them to [Long].
     *
     * @param x The horizontal position.
     * @param y The vertical position.
     */
    constructor(x: Int, y: Int): this(x.toLong(), y.toLong())

    /**
     * Calculates the [Manhattan distance](https://simple.wikipedia.org/wiki/Manhattan_distance) to another coordinate.
     *
     * The Manhattan distance is the sum of the absolute differences of the coordinates.
     * It represents the shortest path distance when movement is restricted to horizontal and vertical steps.
     *
     * Example:
     * ```
     * val a = Coordinate(1, 1)
     * val b = Coordinate(4, 5)
     * a.manhattanDistance(b)  // |1-4| + |1-5| = 3 + 4 = 7
     * ```
     *
     * @param to The destination coordinate.
     * @return The Manhattan distance between this coordinate and [to].
     *
     * @see manhattanDistance
     */
    fun manhattanDistance(to: Coordinate) = abs(x - to.x) + abs(y - to.y)

    /**
     * Calculates the Manhattan distance to the coordinate at position ([x], [y]).
     *
     * Convenience method that creates a temporary [Coordinate] and calculates the distance to it.
     *
     * @param x The x-coordinate of the destination.
     * @param y The y-coordinate of the destination.
     * @return The Manhattan distance to the specified position.
     *
     * @see manhattanDistance
     */
    fun manhattanDistance(x: Long, y: Long) = manhattanDistance(Coordinate(x, y))

    /**
     * Returns a new coordinate moved in the specified [direction] by the given [distance].
     *
     * This is a convenience method that delegates to the directional movement methods.
     *
     * Example:
     * ```
     * val pos = Coordinate(5, 5)
     * pos.translate(Direction.NORTH, 3)  // Coordinate(5, 2)
     * pos.translate(Direction.EAST, 2)   // Coordinate(7, 5)
     * ```
     *
     * @param direction The direction to move (NORTH, SOUTH, EAST, or WEST).
     * @param distance The number of units to move. Defaults to 1.
     * @return A new coordinate at the translated position.
     *
     * @see north
     * @see south
     * @see east
     * @see west
     */
    fun translate(direction: Direction, distance: Long = 1) = when (direction) {
        Direction.NORTH -> north(distance)
        Direction.EAST -> east(distance)
        Direction.SOUTH -> south(distance)
        Direction.WEST -> west(distance)
    }

    /**
     * Returns a new coordinate moved north (up) by the specified [distance].
     *
     * North decreases the y-coordinate following screen/grid conventions where y increases downward.
     *
     * @param distance The number of units to move north. Defaults to 1.
     * @return A new coordinate with y decreased by [distance].
     *
     * @see south
     * @see translate
     */
    fun north(distance: Long = 1): Coordinate = Coordinate(x, y - distance)

    /**
     * Returns a new coordinate moved east (right) by the specified [distance].
     *
     * East increases the x-coordinate.
     *
     * @param distance The number of units to move east. Defaults to 1.
     * @return A new coordinate with x increased by [distance].
     *
     * @see west
     * @see translate
     */
    fun east(distance: Long = 1): Coordinate = Coordinate(x + distance, y)

    /**
     * Returns a new coordinate moved south (down) by the specified [distance].
     *
     * South increases the y-coordinate following screen/grid conventions where y increases downward.
     *
     * @param distance The number of units to move south. Defaults to 1.
     * @return A new coordinate with y increased by [distance].
     *
     * @see north
     * @see translate
     */
    fun south(distance: Long = 1): Coordinate = Coordinate(x, y + distance)

    /**
     * Returns a new coordinate moved west (left) by the specified [distance].
     *
     * West decreases the x-coordinate.
     *
     * @param distance The number of units to move west. Defaults to 1.
     * @return A new coordinate with x decreased by [distance].
     *
     * @see east
     * @see translate
     */
    fun west(distance: Long = 1): Coordinate = Coordinate(x - distance, y)
}
