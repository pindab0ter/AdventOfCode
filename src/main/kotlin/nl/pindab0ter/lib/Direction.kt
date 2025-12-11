package nl.pindab0ter.lib

enum class Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    fun ninetyDegreesClockwise(): Direction = when (this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }
}
