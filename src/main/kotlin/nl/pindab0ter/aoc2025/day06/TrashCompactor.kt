package nl.pindab0ter.aoc2025.day06

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.lib.init
import nl.pindab0ter.lib.joinToULong
import nl.pindab0ter.lib.second
import nl.pindab0ter.lib.transpose

fun main() {
    val input = getInput(2025, 6)

    val grandTotal = input.parse().sumOf(Problem::solve)
    println("The grand total of the answers to each problem added together is: $grandTotal")

    val cephalopodGrandTotal = input.cephalopodMathParse().sumOf(Problem::solve)
    println("The grand total of the answers to each problem solved with Cephalopod math added together is: $cephalopodGrandTotal")
}

fun String.parse() = lines()
    .map { it.trim().split("\\s+".toRegex()) }
    .transpose()
    .map(::Problem)

fun String.cephalopodMathParse(): List<Problem> {
    val operationSymbols = "([*+] *)"
        .toRegex()
        .findAll(lines().last() + " ") // The last column has a newline where a space would go
        .map { matchResult -> matchResult.groupValues.second() }
        .toList()

    val operations: List<(ULong, ULong) -> ULong> = operationSymbols.map {
        when (it.first()) {
            '*' -> times
            '+' -> plus
            else -> throw IllegalArgumentException("Unknown operation: ${it.first()}")
        }
    }

    val columnWidths = operationSymbols.mapIndexed { index, string ->
        // Remove padding between columns, but not for the latest column
        if (index != operationSymbols.count() - 1) string.length - 1
        else string.length
    }

    val numbers = lines()
        .init()
        .map(String::toList)
        // Rows
        .map { row ->
            columnWidths.runningFold(0) { startIndex, width -> startIndex + width + 1 }
                .init()
                .zip(columnWidths) { startIndex, width ->
                    row.subList(startIndex, startIndex + width)
                }
        }
        // Rows divided into columns
        .transpose()
        // Columns divided into rows
        .map { columns ->
            columns
                // Column of numbers read top left to bottom right
                .transpose()
                .reversed()
                // Column of numbers read top right to bottom left
                .map { chars -> chars.mapNotNull(Char::digitToIntOrNull).joinToULong() }
        }

    return numbers
        .zip(operations) { a, b -> Problem(a, b) }
        .reversed()
}

data class Problem(
    val numbers: List<ULong>,
    val operation: (x: ULong, y: ULong) -> ULong,
) {
    constructor(list: List<String>) : this(
        list.init().map(String::toULong), when (list.last()) {
            "*" -> times
            "+" -> plus
            else -> throw IllegalArgumentException("Unknown operation: ${list.last()}")
        }
    )

    fun solve(): ULong = numbers.reduce(operation)
}

val plus = { x: ULong, y: ULong -> x + y }
val times = { x: ULong, y: ULong -> x * y }
