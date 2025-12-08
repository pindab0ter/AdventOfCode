package nl.pindab0ter.aoc2024.day06

import com.github.ajalt.mordant.animation.coroutines.animateInCoroutine
import com.github.ajalt.mordant.animation.textAnimation
import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.terminal.Terminal
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.aoc2024.day06.Direction.*
import nl.pindab0ter.aoc2024.day06.Tile.*
import nl.pindab0ter.lib.Coordinate
import nl.pindab0ter.lib.coordinateOfAny
import nl.pindab0ter.lib.get
import nl.pindab0ter.lib.set
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * The animation doesn't work when running through IntelliJ.
 * Use `./gradleW :runKotlin2024day06` instead, or even better, reroute `installDist` to this class by modifying
 * `mainClass.set()` in `application` in `build.gradle.kts` and run the generated binary:
 * `build/install/AdventOfCode/bin/AdventOfCode`.
 */
fun main() = runBlocking {
    val input = getInput(2024, 6)
    val terminal = Terminal()

    val lab = Lab.fromString(input)

    val animation = terminal
        .textAnimation<Unit> { lab.toString() }
        .animateInCoroutine(fps = 60) { !lab.guardIsInside() }
        .apply { launch { execute() } }

    lab.gallivant(delayBetweenEachStep = 1.milliseconds)

    animation.stop()

    terminal.println("Number of tiles the guard has visited: ${lab.visitedTiles()}")
}

enum class Direction(val representation: Char) {
    NORTH('^'),
    EAST('>'),
    SOUTH('v'),
    WEST('<');

    fun ninetyDegreesClockwise(): Direction = when (this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }

    companion object {
        fun from(char: Char): Direction? = entries.find { it.representation == char }
    }
}

enum class Tile(val recognizedBy: List<Char>) {
    FREE(listOf('.')),
    OBSTRUCTION(listOf('#')),
    GUARD(Direction.entries.map { it.representation }),
    VISITED(listOf());

    companion object {
        fun from(char: Char): Tile? = entries.find { it.recognizedBy.contains(char) }
    }
}

typealias Map = MutableList<MutableList<Tile>>

data class Guard(
    var coordinate: Coordinate,
    var direction: Direction,
) {
    fun coordinateInFront(): Coordinate = when (direction) {
        NORTH -> Coordinate(coordinate.x.toInt(), coordinate.y.toInt() - 1)
        EAST -> Coordinate(coordinate.x.toInt() + 1, coordinate.y.toInt())
        SOUTH -> Coordinate(coordinate.x.toInt(), coordinate.y.toInt() + 1)
        WEST -> Coordinate(coordinate.x.toInt() - 1, coordinate.y.toInt())
    }
}

data class Lab(
    val map: Map,
    val guard: Guard,
) {
    val width = map.first().size
    val height = map.size

    fun guardIsInside(): Boolean = guard.coordinate.x in 0 until width && guard.coordinate.y in 0 until height
    fun visitedTiles(): Int = map.flatten().count { it == VISITED }

    suspend fun gallivant(delayBetweenEachStep: Duration = 0.milliseconds) {
        while (guardIsInside()) {
            val tileWhereGuardWas = guard.coordinate

            while (map[guard.coordinateInFront()] == OBSTRUCTION) {
                guard.direction = guard.direction.ninetyDegreesClockwise()
            }

            guard.coordinate = guard.coordinateInFront()
            if (guardIsInside()) map[guard.coordinate] = GUARD
            map[tileWhereGuardWas] = VISITED

            delay(delayBetweenEachStep)
        }
    }


    override fun toString(): String = map.joinToString("\n") { row ->
        row.joinToString("") { tile ->
            when (tile) {
                FREE -> gray("·")
                OBSTRUCTION -> white("▢")
                GUARD -> brightBlue(
                    when (guard.direction) {
                        NORTH -> "⮝"
                        WEST -> "⮜"
                        SOUTH -> "⮟"
                        EAST -> "⮞"
                    }
                )

                VISITED -> brightGreen("•")
            }
        }
    }

    companion object {
        fun fromString(input: String): Lab {
            val charGrid = input.lines().map { it.toCharArray().toList() }
            val map = charGrid.map { row -> row.map { point -> Tile.from(point)!! }.toMutableList() }.toMutableList()
            val guardCoordinate = charGrid.coordinateOfAny(GUARD.recognizedBy)!!
            val guard = Guard(
                coordinate = guardCoordinate,
                direction = Direction.from(charGrid[guardCoordinate])!!
            )

            return Lab(map, guard)
        }
    }
}
