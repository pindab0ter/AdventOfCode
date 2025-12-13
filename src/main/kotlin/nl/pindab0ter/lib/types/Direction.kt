package nl.pindab0ter.lib.types

/** Cardinal directions. */
enum class Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    fun opposite(): Direction = when (this) {
        NORTH -> SOUTH
        EAST -> WEST
        SOUTH -> NORTH
        WEST -> EAST
    }

    fun ninetyDegreesClockwise(): Direction = when (this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }
}
