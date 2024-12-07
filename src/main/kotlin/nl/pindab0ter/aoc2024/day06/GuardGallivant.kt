package nl.pindab0ter.aoc2024.day06

import com.github.ajalt.mordant.animation.textAnimation
import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextColors.brightWhite
import com.github.ajalt.mordant.terminal.Terminal
import nl.pindab0ter.aoc2024.day06.Direction.*
import nl.pindab0ter.aoc2024.day06.Tile.*
import nl.pindab0ter.common.Coordinate
import nl.pindab0ter.common.coordinateOfAny
import nl.pindab0ter.common.get

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
    TRAVELLED(listOf());

    companion object {
        fun from(char: Char): Tile? = entries.find { it.recognizedBy.contains(char) }
    }
}

typealias Map = List<List<Tile>>

data class Guard(
    val coordinate: Coordinate,
    val direction: Direction,
)

data class Lab(
    val map: Map,
    val guard: Guard,
) {
    override fun toString(): String = map.joinToString("\n") { row ->
        row.joinToString("") { tile ->
            when (tile) {
                FREE -> TextColors.gray("·")
                OBSTRUCTION -> TextColors.white("▢")
                GUARD -> TextColors.brightBlue(when (guard.direction) {
                    NORTH -> "⮝"
                    WEST -> "⮜"
                    SOUTH -> "⮟"
                    EAST -> "⮞"
                })
                TRAVELLED -> TextColors.brightGreen("•")
            }
        }
    }

    companion object {
        fun fromString(input: String): Lab {
            val charGrid = input.lines().map { it.toCharArray().toList() }
            val map = charGrid.map { row -> row.map { point -> Tile.from(point)!! } }
            val guardCoordinate = charGrid.coordinateOfAny(GUARD.recognizedBy)!!
            val guard = Guard(
                coordinate = guardCoordinate,
                direction = Direction.from(charGrid[guardCoordinate]!!)!!
            )

            return Lab(map, guard)
        }
    }
}

fun main() {
//    val input = getInput(2024, 6).parse()

    val terminal = Terminal(interactive = true)

    val testInput = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...
    """.trimIndent()

    var lab = Lab.fromString(testInput)

    val animation = terminal.textAnimation<Lab> { it.toString() }

    while(true) {
//        lab = lab.copy(guard = lab.guard.copy(coordinate = lab.guard.coordinate.copy(y = lab.guard.coordinate.y - 1)))
        animation.update(lab)
        Thread.sleep(500)
    }
}

fun String.parse(): String = TODO()
