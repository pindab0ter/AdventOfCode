package nl.pindab0ter.aoc2019.day03

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.lib.types.Point
import nl.pindab0ter.lib.types.Direction
import nl.pindab0ter.lib.types.Direction.*
import nl.pindab0ter.lib.println
import nl.pindab0ter.lib.collections.second

fun main() {
    val instructions = getInput(2019, 3).parse()
    val paths = instructions.followInstructions()
    val manhattanDistanceToClosestIntersection = paths.manhattanDistanceToClosestIntersection()

    println("The Manhattan distance to the closest intersection from the central port is $manhattanDistanceToClosestIntersection")
}

fun List<Set<Instruction>>.followInstructions(): List<Set<Point>> = map { instructions ->
    instructions.fold(mutableListOf(Point(0, 0))) { path, instruction ->
        repeat(instruction.steps.toInt()) {
            path.add(path.last().advance(instruction.direction))
        }
        path
    }.toSet()
}

fun List<Set<Point>>.manhattanDistanceToClosestIntersection(): Long? {
    val redWire = first()
    val greenWire = second().toSet()

    return redWire.filter { it in greenWire }
        .minus(Point(0L, 0L))
        .minOfOrNull { point -> point.manhattanDistanceTo(0L, 0L) }
}

fun String.parse(): List<Set<Instruction>> = lines().map { line ->
    line.split(",").map { step ->
        val (_, directionString, stepsCountString) = Regex("""(?<direction>[UDLR])(?<steps>\d+)""").find(step)!!.groupValues
        Instruction(directionString.first().toDirection(), stepsCountString.toLong())
    }.toSet()
}

data class Instruction(
    val direction: Direction,
    val steps: Long,
)

fun Char.toDirection() = when (this) {
    'U' -> NORTH
    'D' -> SOUTH
    'L' -> WEST
    'R' -> EAST
    else -> throw IllegalArgumentException("Invalid direction: $this")
}

private fun Point.advance(direction: Direction): Point = when (direction) {
    NORTH -> copy(y = y - 1)
    EAST -> copy(x = x + 1)
    SOUTH -> copy(y = y + 1)
    WEST -> copy(x = x - 1)
}
