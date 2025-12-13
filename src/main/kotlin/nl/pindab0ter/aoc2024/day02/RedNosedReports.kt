package nl.pindab0ter.aoc2024.day02

import arrow.core.tail
import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.aoc2024.day02.Status.*
import nl.pindab0ter.lib.collections.second
import nl.pindab0ter.lib.collections.without

fun main() {
    val input = getInput(2024, 2).parse()

    val safeReportCount = input
        .map { report -> report.status() }
        .count { it != UNSAFE }

    println("Amount of safe reports: $safeReportCount")

    val dampenedSafeReportCount = input
        .map { report -> report.statusWithDampener() }
        .count { it != UNSAFE }

    println("Amount of safe reports using the Problem Dampener: $dampenedSafeReportCount")
}

typealias Level = Int
typealias Report = List<Level>

enum class Status {
    INCREASING, DECREASING, UNSAFE
}

@Suppress("ReplaceRangeToWithRangeUntil")
fun Pair<Level, Level>.status(): Status = when (second) {
    in first + 1..first + 3 -> INCREASING
    in first - 3..first - 1 -> DECREASING
    else -> UNSAFE
}

fun Report.status(): Status {
    val firstPairStatus = Pair(first(), second()).status()

    if (firstPairStatus == UNSAFE) return UNSAFE

    return tail().windowed(2).fold(firstPairStatus) { status, (a, b) ->
        if (Pair(a, b).status() != status) return UNSAFE
        status
    }
}

fun Report.statusWithDampener(): Status = (0 until count())
    .map { i -> this.without(i) }
    .map(Report::status)
    .find { status -> status !== UNSAFE } ?: UNSAFE

fun String.parse(): List<Report> = lines().map { line ->
    Regex("""(\d+(?=\s+)?)""").findAll(line).map { match ->
        match.value.toInt()
    }.toList()
}
