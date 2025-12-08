package nl.pindab0ter.aoc2023.day08

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.lib.lcm

fun main() {
    val (instructions, network) = parse(getInput(2023, 8))

    val stepsToDestination = findStepsToDestination(instructions, network, { equals("AAA") }, { equals("ZZZ") })
    println("Found destination in $stepsToDestination steps")


    val stepsToAllDestinations = findStepsToDestination(instructions, network, { endsWith('A') }, { endsWith('Z') })
    println("Found all destinations in $stepsToAllDestinations steps")
}

typealias Network = Map<String, Node>

data class Node(val left: String, val right: String)

fun findStepsToDestination(
    instructions: String,
    network: Network,
    isStart: String.() -> Boolean,
    isDestination: String.() -> Boolean,
): Long {
    // Accidentally reinvented the `when` statement here.
    val applyInstructions: Map<Char, (Node) -> String> = mapOf('L' to Node::left, 'R' to Node::right)

    tailrec fun followInstructions(currentNode: Node, index: Long = 0L): Long {
        val instruction = instructions[(index % instructions.length).toInt()]
        val nextLocation = applyInstructions[instruction]!!(currentNode)

        return when {
            nextLocation.isDestination() -> index + 1
            else -> followInstructions(network.getValue(nextLocation), index + 1)
        }
    }

    val startingLocations = network.filterKeys(isStart).values
    val stepsToDestinations = startingLocations.map(::followInstructions).toLongArray()

    return lcm(*stepsToDestinations)
}
