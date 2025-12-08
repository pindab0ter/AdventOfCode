package nl.pindab0ter.aoc2015.day01

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.lib.println

fun main() {
    val input = getInput(2015, 1)

    val finalFloor = calculateFinalFloor(input)
    println("The final floor is: $finalFloor")

    val instructionLeadingToBasement = findInstructionLeadingToBasement(input)
    println("\nThe location of the first instruction leading to the basement is: $instructionLeadingToBasement")
}

fun calculateFinalFloor(input: String): Int = input.sumOf { character ->
    if (character == '(') 1 as Int
    else -1
}

fun findInstructionLeadingToBasement(input: String): Int {
    var floor = 0

    input.forEachIndexed { index, character ->
        if (character == '(') floor++ else floor--
        if (floor == -1) return index + 1
    }

    throw IllegalStateException("No instruction leading to the basement found")
}
