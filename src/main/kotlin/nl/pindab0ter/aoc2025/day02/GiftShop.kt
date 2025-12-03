package nl.pindab0ter.aoc2025.day02

import nl.pindab0ter.common.*

fun main() {
    val ranges = getInput(2025, 2).parse()

    val validIds = ranges.flatMap { range -> range.sequencesOfDigitsRepeatedTwice() }.sum()
    val validIds2 = ranges.flatMap { range -> range.sequencesOfDigitsRepeatedAtLeastTwice() }.sum()

    println("Sum of IDs that have a sequence of digits repeated twice: $validIds")
    println("Sum of IDs that have sequences of digits repeated at least twice: $validIds2")
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

fun ULong.windowed(size: Int, step: Int = 1) = (digits - size downTo 0 step step).map { divisor ->
    this / 10uL.pow(divisor) % 10uL.pow(size)
}

fun ULongRange.sequencesOfDigitsRepeatedAtLeastTwice(): List<ULong> = filter { id ->
    val digits = id.digits

    (1..digits / 2)
        .filter { divisor -> digits % divisor == 0 }
        .map { windowSize ->
            id.windowed(windowSize, windowSize)
        }
        .any(List<ULong>::allElementsEqual)
}
