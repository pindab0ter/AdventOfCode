package nl.pindab0ter.aoc2024.day05

import nl.pindab0ter.aoc.getInput

typealias PageOrderingRule = Pair<Int, Int>
typealias Update = List<Int>

fun main() {
    val (pageOrderingRules, updates) = getInput(2024, 5).parse()

    val correctlyOrderedUpdates = updates.filter { pageOrderingRules.all { rule -> it.conformsTo(rule) } }
    val sumOfMiddlePages = correctlyOrderedUpdates.sumOfMiddlePages()

    println("Sum of the middle page numbers of all correctly ordered updates: $sumOfMiddlePages")

    val comparator = PageOrderingRulesComparator(pageOrderingRules)
    val incorrectlyOrderedUpdates = updates.filterNot { pageOrderingRules.all { rule -> it.conformsTo(rule) } }
    val sumOfOrderedMiddlePagesOfIncorrectlyOrderedUpdates = incorrectlyOrderedUpdates
        .map { it.sortedWith(comparator) }
        .sumOfMiddlePages()

    println("Sum of the middle page numbers of all incorrectly ordered updates after ordering them: $sumOfOrderedMiddlePagesOfIncorrectlyOrderedUpdates")
}

fun String.parse(): Pair<List<PageOrderingRule>, List<Update>> {
    val (pageOrderingRulesString, updatesString) = this.split("\n\n")

    val pageOrderingRules = pageOrderingRulesString.lines().map { line ->
        val (x, y) = line.split("|").map(String::toInt)
        x to y
    }

    val updates = updatesString.lines().map { line ->
        line.split(",").map(String::toInt)
    }

    return pageOrderingRules to updates
}

fun Update.conformsTo(rule: PageOrderingRule): Boolean =
    !contains(rule.first) || !contains(rule.second) || indexOf(rule.first) < indexOf(rule.second)

fun List<Update>.sumOfMiddlePages(): Int = sumOf { update -> update[update.count() / 2] }

class PageOrderingRulesComparator(
    private val rules: List<PageOrderingRule>,
) : Comparator<Int> {
    override fun compare(o1: Int, o2: Int): Int = when {
        rules.any { rule -> rule.first == o1 && rule.second == o2 } -> -1
        rules.any { rule -> rule.first == o2 && rule.second == o1 } -> 1
        else -> 0
    }
}
