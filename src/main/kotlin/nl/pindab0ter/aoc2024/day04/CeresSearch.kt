package nl.pindab0ter.aoc2024.day04

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.lib.collections.rotate45Degrees
import nl.pindab0ter.lib.collections.rotate45DegreesAntiClockwise
import nl.pindab0ter.lib.collections.transpose

fun main() {
    val input = getInput(2024, 4).lines().map(String::toList)

    val xmasCount = input.xmasOccurrences()

    println("Amount of times XMAS can be found in this word search: $xmasCount")

    val crossMassCount = input.crossMassOccurrences()

    println("Amount of times an X-MAS appears in this word search: $crossMassCount")
}


fun List<List<Char>>.xmasOccurrences(): Int = this
    .plus(this.transpose())
    .plus(this.rotate45Degrees())
    .plus(this.rotate45DegreesAntiClockwise())
    .sumOf { line ->
        line
            .joinToString("")
            .windowed(4)
            .count { it == "XMAS" || it == "SAMX" }
    }

fun List<List<Char>>.crossMassOccurrences(): Int = (0..this.count() - 3).sumOf { y ->
    (0..this.first().count() - 3).count { x ->
        val a = "${this[y][x]}${this[y + 1][x + 1]}${this[y + 2][x + 2]}"
        val b = "${this[y][x + 2]}${this[y + 1][x + 1]}${this[y + 2][x]}"

        (a == "MAS" || a == "SAM") && (b == "MAS" || b == "SAM")
    }
}
