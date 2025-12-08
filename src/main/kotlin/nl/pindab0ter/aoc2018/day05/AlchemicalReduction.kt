package nl.pindab0ter.aoc2018.day05

import nl.pindab0ter.aoc.getInput

fun main() = getInput(2018, 5).let { input ->
    print(
        """
        --- Day 5: Alchemical Reduction ---

        Part one: How many units remain after fully reacting the polymer you scanned?
        ${reduce(input).length}

        Part two: What is the length of the shortest polymer you can produce by removing all units of exactly one type and fully reacting the result?
        ${shortestPolymer(input)}

        """.trimIndent()
    )
}

fun shortestPolymer(input: String): Int = ('a'..'z').fold(emptyList<String>()) { acc, unit ->
    acc + reduce(input.filter { it.equals(unit, true).not() })
}.minBy { it.length }.length

fun reduce(input: String): String = input.fold("") { acc: String, c: Char ->
    if (acc.isBlank() || acc.last().formsPairWith(c).not()) acc + c
    else acc.dropLast(1)
}

fun Char.formsPairWith(other: Char): Boolean = equalsCase(other).not() && equals(other, true)

fun Char.equalsCase(other: Char): Boolean = isUpperCase() == other.isUpperCase()
