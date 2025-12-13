package nl.pindab0ter.aoc2025.day04

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.lib.collections.Grid
import nl.pindab0ter.lib.println
import nl.pindab0ter.lib.collections.toGrid

fun main() {
    val grid = getInput(2025, 4).parse()

    val accessibleRollCount = grid.accessibleRollsMarked().count { it == 'x' }
    println("Amount of rolls of paper accessible by forklift on the first go: $accessibleRollCount")

    val accessibleRollsRemoved = grid.countRolls() - grid.removeAccessibleRolls().countRolls()
    println("Total amount of accessible rolls of paper removed: $accessibleRollsRemoved")
}

fun String.parse(): Grid<Char> = lines().map(String::toList).toGrid()

fun Iterable<Char>.countRolls() = count { it == '@' }

fun Grid<Char>.accessibleRollsMarked(): Grid<Char> = mapIndexed { point, value ->
    when (value) {
        'x' -> '.'
        '@' if neighbours(point).countRolls() < 4 -> 'x'
        else -> value
    }
}

fun Grid<Char>.removeAccessibleRolls(): Grid<Char> = generateSequence(this) { it.accessibleRollsMarked() }
    .zipWithNext()
    .first { (previous, current) -> current.countRolls() == previous.countRolls() }
    .second
