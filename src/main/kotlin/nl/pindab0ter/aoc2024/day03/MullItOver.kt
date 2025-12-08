package nl.pindab0ter.aoc2024.day03

import arrow.core.tail
import nl.pindab0ter.aoc.getInput
import kotlin.text.RegexOption.DOT_MATCHES_ALL

fun main() {
    val input = getInput(2024, 3)

    val multiplications = input.findInstructions()
    println("Sum of multiplications: ${multiplications.sumOf(Multiplication::perform)}")

    val enabledMultiplications = input.removeDisabledInstructions().findInstructions()
    println("Sum of enabled multiplications: ${enabledMultiplications.sumOf(Multiplication::perform)}")
}

typealias Multiplication = Pair<Int, Int>

fun Multiplication.perform() = first * second

fun String.removeDisabledInstructions(): String =
    replace(Regex("""don't\(\).*?(do\(\)|$)""", DOT_MATCHES_ALL), "")

fun String.findInstructions(): List<Multiplication> = Regex("""mul\((\d+),(\d+)\)""")
    .findAll(this)
    .map { matchResult ->
        val (x, y) = matchResult.groupValues.tail().map(String::toInt)
        Multiplication(x, y)
    }
    .toList()
