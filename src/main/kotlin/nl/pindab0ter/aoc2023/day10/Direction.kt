package nl.pindab0ter.aoc2023.day10

import nl.pindab0ter.lib.Coordinate
import nl.pindab0ter.lib.getOrNull

/**
 * Represents a direction in a 2D grid.
 *
 * @property dx The displacement in the x-axis.
 * @property dy The displacement in the y-axis.
 */
enum class Direction(val dx: Int, val dy: Int) {
    NORTH(0, -1) {
        override fun opposite() = SOUTH
    },
    EAST(1, 0) {
        override fun opposite() = WEST
    },
    SOUTH(0, 1) {
        override fun opposite() = NORTH
    },
    WEST(-1, 0) {
        override fun opposite() = EAST
    };

    abstract fun opposite(): Direction

    companion object {
        private fun Coordinate.toThe(direction: Direction) = copy(x = x + direction.dx, y = y + direction.dy)

        fun getDirectionsPointingTo(
            coordinates: Coordinate,
            grid: List<List<Char>>,
        ): Set<Direction> = setOf(NORTH, EAST, SOUTH, WEST).mapNotNull { direction ->
            val character = grid.getOrNull(coordinates.toThe(direction))
            Section.from(character)?.directions?.firstOrNull { it == direction.opposite() }
        }.toSet()
    }
}
