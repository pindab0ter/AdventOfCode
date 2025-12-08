package nl.pindab0ter.aoc2018.day01

import nl.pindab0ter.aoc.getInput

fun main() = getInput(2018, 1)
    .lines()
    .map(String::toInt)
    .let { input ->
        print(
            """
            --- Day 1: Chronal Calibrations ---

            Part one: Starting with a frequency of zero, what is the resulting frequency after all of the changes in frequency have been applied?
            ${findFinalFrequency(input)}

            Part two: What is the first frequency your device reaches twice?
            ${findFirstRepeatedFrequency(input)}

            """.trimIndent()
        )
    }

fun findFinalFrequency(input: List<Int>): Int = input.sum()

fun findFirstRepeatedFrequency(input: List<Int>): Int {
    var previousFrequency = 0
    val seen = mutableSetOf(previousFrequency)
    while (true) {
        for (element in input) {
            val newFrequency = element + previousFrequency
            if (seen.contains(newFrequency)) return newFrequency
            else {
                seen.add(newFrequency)
                previousFrequency = newFrequency
            }
        }
    }
}
