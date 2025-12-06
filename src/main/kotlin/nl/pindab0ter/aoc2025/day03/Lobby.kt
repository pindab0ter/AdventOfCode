package nl.pindab0ter.aoc2025.day03

import nl.pindab0ter.common.getInput
import nl.pindab0ter.common.pow
import nl.pindab0ter.common.println
import nl.pindab0ter.common.without

typealias Joltage = ULong
typealias JoltageRating = Int
typealias Bank = List<JoltageRating>

fun main() {
    val banks = getInput(2025, 3).parse()

    val twoBatteryJoltages = banks.map { bank -> bank.getTwoBatteryJoltage() }
    println("Total output joltage of the banks for two batteries per bank combined: ${twoBatteryJoltages.sum()}")

    val twelveBatteryJoltages = banks.map { bank -> bank.getTwelveBatteryJoltage() }
    println("Total output joltage of the banks for twelve batteries per bank combined: ${twelveBatteryJoltages.sum()}")
}

fun String.parse(): List<Bank> = lines().map { line ->
    line.toList().map { numChar ->
        numChar.digitToInt()
    }
}

fun Bank.getTwoBatteryJoltage(): Joltage {
    var left = max()
    var right: Int? = null

    do {
        try {
            right = drop(indexOf(left) + 1).max()
        } catch (_: NoSuchElementException) {
            left = without(indexOf(left)).max()
        }
    } while (right === null)

    return left.toULong() * 10uL + right.toULong()
}

fun List<Int>.joinToULong(): ULong = foldIndexed(0uL) { index, acc, x ->
    acc + x.toULong() * (10uL.pow(size - index - 1))
}

fun Bank.getTwelveBatteryJoltage(): Joltage {
    val joltages = mutableListOf(dropLast(12 - 1).max())
    var index = indexOf(joltages.first())

    do {
        // Disregard batteries to the left of already picked ones and leave enough room to reach the target count
        // From the remaining options, select the highest value battery
        val choices = drop(index + 1).dropLast(12 - joltages.size - 1)
        val highestValueBattery = choices.max()
        joltages.add(highestValueBattery)
        index += choices.indexOf(highestValueBattery) + 1
    } while (joltages.size < 12)

    return joltages.joinToULong()
}
