package nl.pindab0ter.aoc2025.day08

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.lib.DisjointSet
import nl.pindab0ter.lib.println
import nl.pindab0ter.lib.productOf
import nl.pindab0ter.lib.sqrt
import java.util.*

data class Vertex(val x: Long, val y: Long, val z: Long) : Comparable<Vertex> {
    override fun compareTo(other: Vertex): Int = compareValuesBy(this, other, Vertex::x, Vertex::y, Vertex::z)
}

val Pair<Vertex, Vertex>.distance
    get() = listOf(
        (first.x - second.x) * (first.x - second.x),
        (first.y - second.y) * (first.y - second.y),
        (first.z - second.z) * (first.z - second.z),
    ).sum().sqrt()

fun main() {
    val junctionBoxes = getInput(2025, 8).parse()

    val productOfThreeLargestCircuits = junctionBoxes.part1(1000)
    println("The product of the three largest circuits: $productOfThreeLargestCircuits")
}

fun String.parse(): List<Vertex> =
    lines().map { line -> line.split(",").map(String::toLong) }.map { (x, y, z) -> Vertex(x, y, z) }

fun List<Vertex>.part1(closestPairLimit: Int): Int = findClosestPairs(closestPairLimit)
    .stringTogether()
    .sortedByDescending(Set<Vertex>::count)
    .take(3)
    .productOf(Set<Vertex>::count)

fun List<Vertex>.findClosestPairs(limit: Int): List<Pair<Vertex, Vertex>> {
    val queue = PriorityQueue(compareByDescending(Pair<Vertex, Vertex>::distance))

    forEachIndexed { index, vertex ->
        drop(index + 1).forEach { otherVertex ->
            val pair = minOf(vertex, otherVertex) to maxOf(vertex, otherVertex)

            if (queue.size < limit) {
                queue.offer(pair)
            } else if (pair.distance < queue.peek().distance) {
                queue.poll()
                queue.offer(pair)
            }
        }
    }

    return generateSequence { queue.poll() }.toList().reversed()
}

fun List<Pair<Vertex, Vertex>>.stringTogether(): Collection<Set<Vertex>> {
    val disjointSet = DisjointSet(flatMap { pair -> pair.toList().toSet() })
    forEach { (a, b) -> disjointSet.union(a, b) }
    return disjointSet.components
}
