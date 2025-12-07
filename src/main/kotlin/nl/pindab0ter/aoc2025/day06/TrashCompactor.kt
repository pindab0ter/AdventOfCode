package nl.pindab0ter.aoc2025.day06

import nl.pindab0ter.common.getInput
import nl.pindab0ter.common.init
import nl.pindab0ter.common.transpose

fun main() {
    val input = getInput(2025, 6).parse()

    val result = input.sumOf(Problem::solve)

    println("The grand total of the answers to each problem added together is: $result")
}

fun String.parse() = lines()
    .map { it.trim().split("\\s+".toRegex()) }
    .transpose()
    .map(::Problem)

data class Problem(
    val numbers: List<ULong>,
    val operation: (x: ULong, y: ULong) -> ULong,
) {
    constructor(list: List<String>) : this(
        list.init().map(String::toULong), when (list.last()) {
            "*" -> { x: ULong, y: ULong -> x * y }
            "+" -> { x: ULong, y: ULong -> x + y }
            else -> throw IllegalArgumentException("Unknown operation: ${list.last()}")
        })

    fun solve(): ULong = numbers.reduce(operation)
}
