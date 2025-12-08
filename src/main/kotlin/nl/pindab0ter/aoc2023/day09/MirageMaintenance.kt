package nl.pindab0ter.aoc2023.day09

import nl.pindab0ter.aoc.getInput

fun main() {
    val sequences = parse(getInput(2023, 9))

    val sumOfExtrapolatedFollowingValues = sequences.sumOf(::extrapolateValue)
    println("The sum of the extrapolated next values is $sumOfExtrapolatedFollowingValues")

    val sumOfExtrapolatedPrecedingValues = sequences.map { it.asReversed() }.sumOf(::extrapolateValue)
    println("The sum of the extrapolated preceding values is $sumOfExtrapolatedPrecedingValues")
}

fun parse(input: String) = input.lines().map { line -> line.split(' ').map(String::toInt) }

fun extrapolateValue(sequence: List<Int>): Int {
    val differences = sequence.windowed(2).map { (a, b) -> b - a }

    return when {
        differences.all { it == 0 } -> sequence.last() + differences.first()
        else -> sequence.last() + extrapolateValue(differences)
    }
}
