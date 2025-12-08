package nl.pindab0ter.aoc2023.day11

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.lib.println

fun main() {
    val input = getInput(2023, 11)

    val universe = parse(input)

    println(
        "Sum of shortest distances for a universe expanded once: ${
            universe.sumOfDistancesBetweenGalaxies(1)
        }"
    )
    println(
        "Sum of shortest distances for a universe expanded one MILLION times: ${
            universe.sumOfDistancesBetweenGalaxies(1_000_000)
        }"
    )
}
