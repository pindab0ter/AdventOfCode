package nl.pindab0ter.aoc2025.day05

import nl.pindab0ter.common.getInput
import nl.pindab0ter.common.println
import nl.pindab0ter.common.replaceLast
import kotlin.math.max

fun main() {
    val (ranges, ingredients) = getInput(2025, 5).parse()

    val availableFreshIngredientCount = ingredients.filterSpoiled(ranges).count()
    println("Amount of available ingredients considered fresh: $availableFreshIngredientCount")

    val freshIngredientIdCount = ranges.countUniqueIds()
    println("Amount of unique ingredient IDs considered fresh: $freshIngredientIdCount")
}

fun String.parse(): Pair<List<ULongRange>, List<ULong>> {
    val (ranges, ingredients) = split("\n\n")

    return ranges.lines().map {
        val (from, to) = it.split("-")
        (from.toULong()..to.toULong())
    } to ingredients.lines().map(String::toULong)
}

fun List<ULong>.filterSpoiled(freshRanges: List<ULongRange>): List<ULong> =
    filter { ingredient -> freshRanges.any { it.contains(ingredient) } }

fun List<ULongRange>.countUniqueIds(): ULong = this
    .sortedBy(ULongRange::first)
    .drop(1)
    .fold(listOf(minBy(ULongRange::first))) { acc, current ->
        if (current.first <= acc.last().last) {
            acc.replaceLast { it.first..max(it.last, current.last) }
        } else {
            acc.plusElement(current)
        }
    }
    .sumOf { it.last - it.first + 1uL }
