package nl.pindab0ter.aoc2025.day05

import nl.pindab0ter.common.getInput
import nl.pindab0ter.common.println

fun main() {
    val (freshRanges, ingredients) = getInput(2025, 5).parse()

    val freshIngredientCount = ingredients.filterSpoiled(freshRanges)

    println("Amount of ingredients considered fresh: $freshIngredientCount")
}

typealias IngredientID = ULong
typealias FreshIngredientRange = ULongRange

fun String.parse(): Pair<List<FreshIngredientRange>, List<IngredientID>> {
    val (ranges, ingredients) = split("\n\n")

    return ranges.lines().map {
        val (from, to) = it.split("-")
        (from.toULong()..to.toULong())
    } to ingredients.lines().map(String::toULong)
}

fun List<IngredientID>.filterSpoiled(freshRanges: List<FreshIngredientRange>): List<IngredientID> =
    filter { ingredient -> freshRanges.any { it.contains(ingredient) } }
