package nl.pindab0ter.aoc2025.day02

import nl.pindab0ter.common.*

fun main() {
    val ranges = getInput(2025, 2).parse()

    val validIds = ranges.flatMap { range -> range.sequencesOfDigitsRepeatedTwice() }.sum()

    println("Sum of IDs that have a sequence of digits repeated twice: $validIds")
}

fun String.parse(): List<ULongRange> = split(',')
    .map { rangeString ->
        val (from, to) = rangeString.split('-')
        from.toULong()..to.toULong()
    }

fun ULongRange.sequencesOfDigitsRepeatedTwice(): List<ULong> = filter { id ->
    val digits = id.digits

    if (digits.isOdd()) return@filter false

    val halfMagnitude = 10uL.pow(digits / 2)
    id / halfMagnitude == id % halfMagnitude
}
