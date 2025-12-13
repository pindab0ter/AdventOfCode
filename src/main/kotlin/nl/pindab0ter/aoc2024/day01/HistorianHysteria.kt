package nl.pindab0ter.aoc2024.day01

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.lib.collections.second
import nl.pindab0ter.lib.collections.transpose
import kotlin.math.abs

fun main() = getInput(2024, 1).parse().let { input ->
    val totalDistance = input.totalDistance()
    println("Total distance: $totalDistance")

    val similarityScore = input.similarityScore()
    println("Similarity score: $similarityScore")
}

fun List<List<Int>>.totalDistance(): Int = map(List<Int>::sorted)
    .let { it.first().zip(it.second()) }
    .sumOf { abs(it.first - it.second) }

fun List<List<Int>>.similarityScore(): Int = second()
    .fold(mapOf<Int, Int>()) { acc, number ->
        acc + Pair(number, acc.getOrDefault(number, 0) + 1)
    }
    .let { occurrences ->
        first().sumOf { it * occurrences.getOrDefault(it, 0) }
    }

private fun String.parse(): List<List<Int>> = Regex("""(\d+)\s+(\d+)""")
    .findAll(this)
    .map { matchResult ->
        matchResult.destructured.toList().map(String::toInt)
    }
    .toList()
    .transpose()
