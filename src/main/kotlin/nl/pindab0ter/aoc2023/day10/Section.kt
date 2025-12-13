package nl.pindab0ter.aoc2023.day10

import nl.pindab0ter.lib.types.Direction
import nl.pindab0ter.lib.types.Direction.*

enum class Section(val directions: Set<Direction>, val representation: String) {
    VERTICAL(setOf(NORTH, SOUTH), "│"),
    HORIZONTAL(setOf(EAST, WEST), "─"),
    TOP_LEFT_BEND(setOf(SOUTH, EAST), "╭"),
    TOP_RIGHT_BEND(setOf(WEST, SOUTH), "╮"),
    BOTTOM_LEFT_BEND(setOf(EAST, NORTH), "╰"),
    BOTTOM_RIGHT_BEND(setOf(NORTH, WEST), "╯");

    companion object {
        private val sections = mapOf(
            '|' to VERTICAL,
            '-' to HORIZONTAL,
            'L' to BOTTOM_LEFT_BEND,
            'J' to BOTTOM_RIGHT_BEND,
            '7' to TOP_RIGHT_BEND,
            'F' to TOP_LEFT_BEND,
        )

        fun from(directions: Set<Direction>): Section = entries.first { it.directions.containsAll(directions) }
        fun from(char: Char?): Section? = sections[char]
    }
}
