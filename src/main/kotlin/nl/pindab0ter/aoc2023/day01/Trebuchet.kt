package nl.pindab0ter.aoc2023.day01

import nl.pindab0ter.common.getInput
import nl.pindab0ter.common.timing

fun main() {
    val lines = getInput(2023, 1).lines()

    timing("Calculating the calibration value") {
        val result = lines.sumOf(::getCalibrationValue)
        println(result)
    }
}

val digitNames = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9,
)

fun getCalibrationValue(line: String): Int {
    val digits = line.indices.fold(listOf<Int>()) { acc, i ->
        val remainder = line.substring(i)
        val firstCharacter: Char = remainder.first()

        if (firstCharacter.isDigit()) {
            return@fold acc.plus(firstCharacter.toString().toInt())
        }

        val digit = digitNames.entries.firstOrNull { (name, _) ->
            remainder.startsWith(name)
        }?.value

        if (digit != null) {
            return@fold acc.plus(digit)
        }

        return@fold acc
    }

    return "${digits.first()}${digits.last()}".toInt()
}
