package nl.pindab0ter.aoc2025.day03

import nl.pindab0ter.common.getInput
import nl.pindab0ter.common.without

typealias Joltage = Int
typealias JoltageRating = Int
typealias Bank = List<JoltageRating>

fun main() {
    val banks = getInput(2025, 3).parse()

    val joltages = banks.map { bank -> bank.getJoltage() }

    println("Total output joltage of all battery banks combined: ${joltages.sum()}")
}

fun String.parse(): List<Bank> = lines().map { it.toList().map(Char::digitToInt) }

fun Bank.getJoltage(): Joltage {
    var left = max()
    var right: Int? = null

    do {
        try {
            right = drop(indexOf(left) + 1).max()
        } catch (_: NoSuchElementException) {
            left = without(indexOf(left)).max()
        }
    } while (right === null)

    return left * 10 + right
}
