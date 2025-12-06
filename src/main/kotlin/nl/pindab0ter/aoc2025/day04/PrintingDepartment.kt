package nl.pindab0ter.aoc2025.day04

import nl.pindab0ter.common.*

fun main() {
    val grid = getInput(2025, 4).parse()

    val accessibleRolls = grid.accessibleRollsMarked()

    println("Amount of rolls of paper accessible by forklift: ${grid.count { it == 'x' }}")
}

fun String.parse(): Grid<Char> = lines().map(String::toList).toGrid()

fun Grid<Char>.accessibleRollsMarked(): Grid<Char> = mapIndexed { x, y, value ->
    when {
        value == '@' && neighbours(x, y).count { it == '@' } < 4 -> 'x'
        else -> value
    }
}

