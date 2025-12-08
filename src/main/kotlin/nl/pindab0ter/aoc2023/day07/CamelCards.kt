package nl.pindab0ter.aoc2023.day07

import nl.pindab0ter.aoc.getInput


fun main() {
    val handsWithoutJokers = parse(getInput(2023, 7), withJokers = false)
    val handsWithJokers = parse(getInput(2023, 7), withJokers = true)

    println("Total winnings for all hands (without jokers): ${handsWithoutJokers.totalWinnings()}")
    println("Total winnings for all hands (with jokers):    ${handsWithJokers.totalWinnings()}")
}

fun parse(input: String, withJokers: Boolean): List<Hand> = input.lines().map { line -> Hand(line, withJokers) }

fun List<Hand>.totalWinnings(): Int = indices.zip(sorted().reversed()).sumOf { (index, hand) -> hand.bid * (index + 1) }
