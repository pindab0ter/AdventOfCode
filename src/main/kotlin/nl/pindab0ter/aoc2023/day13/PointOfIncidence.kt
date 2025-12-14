package nl.pindab0ter.aoc2023.day13

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.lib.collections.Grid

fun main() {
    val input = getInput(2023, 13)
    val patterns = parse(input)

    val summaryOfAllNotes = getSummaryOfAllNotes(patterns)
    println("Summary of all notes: $summaryOfAllNotes")
}

fun getSummaryOfAllNotes(patterns: List<Grid<Surface>>): Int {
    return patterns.sumOf { pattern -> locationOfVerticalReflectionLine(pattern.columns()) ?: 0 } +
            patterns.sumOf { pattern -> locationOfVerticalReflectionLine(pattern.rows()) ?: 0 } * 100
}

/**
 * Calculates the 1-indexed location of the line that is the center of the largest vertical reflection in the given
 * [grid], or `0` if there is none.
 */
fun locationOfVerticalReflectionLine(grid: List<List<Surface>>): Int? = (1 until grid.size)
    .filter { y -> grid[y] == grid[y - 1] }
    .mapNotNull { reflectionLine ->
        val distanceToEdge = minOf(reflectionLine, grid.size - reflectionLine) + 1

        // Start at the first column past the one we know is symmetrical (index + 2)
        val reflectionReachesEnd = (1 until distanceToEdge).all { y ->
            grid[reflectionLine + y - 1] == grid[reflectionLine - y]
        }

        if (!reflectionReachesEnd) return@mapNotNull null

        reflectionLine to distanceToEdge
    }
    .maxByOrNull { (_, reflectionSize) -> reflectionSize }
    ?.first
