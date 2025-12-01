package nl.pindab0ter.aoc2025.day01

import nl.pindab0ter.aoc2025.day01.Rotation.Direction
import nl.pindab0ter.aoc2025.day01.Rotation.Direction.LEFT
import nl.pindab0ter.aoc2025.day01.Rotation.Direction.RIGHT
import nl.pindab0ter.common.getInput
import nl.pindab0ter.common.println

data class Rotation(val direction: Direction, val steps: Int) {
    override fun toString(): String = "$direction$steps"

    enum class Direction {
        LEFT, RIGHT;

        companion object {
            fun from(representation: Char): Direction = when (representation) {
                'L' -> LEFT
                'R' -> RIGHT
                else -> throw IllegalArgumentException("Unknown rotation direction: $representation")
            }
        }

        override fun toString(): String = when (this) {
            LEFT -> "L"
            RIGHT -> "R"
        }
    }
}

data class Dial(val position: Int) {
    fun turn(rotation: Rotation) = Dial(
        when (rotation.direction) {
            LEFT -> this.position - rotation.steps
            RIGHT -> this.position + rotation.steps
        }.mod(100)
    )
}

fun main() {
    val directions = getInput(2025, 1).parse()

    val dialLog = followDirections(Dial(50), directions)

    println("The amount of times the dial ended on 0 while following the directions: ${dialLog.count { it.position == 0 }}")
}

fun String.parse(): List<Rotation> = lines().map {
    Rotation(
        direction = Direction.from(it.first()),
        steps = it.drop(1).toInt()
    )
}

fun followDirections(
    startingPosition: Dial,
    directions: List<Rotation>,
) = directions.fold(listOf(startingPosition)) { acc: List<Dial>, rotation: Rotation ->
    acc.plus(acc.last().turn(rotation))
}

