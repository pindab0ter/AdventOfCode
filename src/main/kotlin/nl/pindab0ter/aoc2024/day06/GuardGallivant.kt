package nl.pindab0ter.aoc2024.day06

import com.github.ajalt.mordant.animation.coroutines.animateInCoroutine
import com.github.ajalt.mordant.animation.textAnimation
import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.terminal.Terminal
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.aoc2024.day06.Tile.*
import nl.pindab0ter.lib.collections.get
import nl.pindab0ter.lib.collections.set
import nl.pindab0ter.lib.collections.getOrNull
import nl.pindab0ter.lib.collections.pointOfFirst
import nl.pindab0ter.lib.types.Direction.*
import nl.pindab0ter.lib.types.Point
import nl.pindab0ter.lib.types.Direction
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

enum class Tile(val representation: List<Char>) {
    FREE(listOf('.')),
    OBSTRUCTION(listOf('#')),
    GUARD(Direction.entries.map {
        when (it) {
            NORTH -> '^'
            EAST -> '>'
            SOUTH -> 'v'
            WEST -> '<'
        }
    }),
    VISITED(listOf());

    companion object {
        fun from(char: Char): Tile? = entries.find { it.representation.contains(char) }
    }
}

typealias Map = MutableList<MutableList<Tile>>

data class Guard(
    var point: Point,
    var direction: Direction,
) {
    fun pointInFront(): Point = point.translate(direction)
}

data class Lab(
    val map: Map,
    val guard: Guard,
) {
    val width = map.first().size
    val height = map.size

    fun guardIsInside(): Boolean = guard.point.x in 0 until width && guard.point.y in 0 until height
    fun visitedTiles(): Int = map.flatten().count { it == VISITED }

    suspend fun gallivant(delayBetweenEachStep: Duration = 0.milliseconds) {
        while (guardIsInside()) {
            val tileWhereGuardWas = guard.point

            while (map.getOrNull(guard.pointInFront()) == OBSTRUCTION) {
                guard.direction = guard.direction.ninetyDegreesClockwise()
            }

            guard.point = guard.pointInFront()
            if (guardIsInside()) map[guard.point] = GUARD
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
            val guardPoint = charGrid.pointOfFirst { it in GUARD.representation }!!
            val guard = Guard(
                point = guardPoint,
                direction = when (val char = charGrid[guardPoint]) {
                    '^' -> NORTH
                    '>' -> EAST
                    'v' -> SOUTH
                    '<' -> WEST
                    else -> throw IllegalArgumentException("Unknown direction: $char")
                }
            )

            return Lab(map, guard)
        }
    }
}
