package nl.pindab0ter.aoc2025.day01

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.aoc2025.day01.Instruction.Direction
import nl.pindab0ter.aoc2025.day01.Instruction.Direction.LEFT
import nl.pindab0ter.aoc2025.day01.Instruction.Direction.RIGHT
import nl.pindab0ter.lib.println

fun main() {
    val instructions = getInput(2025, 1).parse()

    val dialLog = followDirections(Dial(), instructions)

    println("The amount of times the dial ended on 0 while following the directions: ${dialLog.count { it.position == 0 }}")
    println("The amount of times the dial passed 0 while following the directions: ${dialLog.last().timesPointedToZero}")
}

fun String.parse(): List<Instruction> = lines().map {
    Instruction(
        direction = Direction.from(it.first()),
        clicks = it.drop(1).toInt()
    )
}

data class Instruction(val direction: Direction, val clicks: Int) {
    override fun toString(): String = "$direction$clicks"

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
            LEFT -> "↺"
            RIGHT -> "↻"
        }
    }
}

class Dial(val position: Int = 50, val timesPointedToZero: Int = 0) {
    fun turn(instruction: Instruction): Dial {
        var currentPosition = position
        var newTimesPointedToZero = timesPointedToZero

        repeat(instruction.clicks) {
            when (instruction.direction) {
                LEFT -> currentPosition--
                RIGHT -> currentPosition++
            }

            when (currentPosition) {
                -1 -> currentPosition = 99
                100 -> currentPosition = 0
            }

            if (currentPosition == 0) newTimesPointedToZero++
        }

        return Dial(currentPosition, newTimesPointedToZero)
    }

    override fun toString(): String = "Dial(position=${position}, timesPointedToZero=${timesPointedToZero})"
}

fun followDirections(
    startingPosition: Dial,
    directions: List<Instruction>,
) = directions.fold(listOf(startingPosition)) { acc: List<Dial>, instruction: Instruction ->
    acc.plus(acc.last().turn(instruction))
}

