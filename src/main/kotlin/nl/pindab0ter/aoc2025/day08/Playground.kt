package nl.pindab0ter.aoc2025.day08

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.lib.collections.DisjointSet
import nl.pindab0ter.lib.println
import nl.pindab0ter.lib.collections.productOf
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

    val sumOfXsOfLastPair = junctionBoxes.part2()
    println("The sum of the x coordinates of the two final junction boxes to be connected: $sumOfXsOfLastPair")
}

fun String.parse(): List<Vertex> = lines()
    .map { line -> line.split(",").map(String::toLong) }
    .map { (x, y, z) -> Vertex(x, y, z) }

fun List<Vertex>.part1(closestPairLimit: Int): Int = findClosestPairs(closestPairLimit)
    .stringTogether()
    .sortedByDescending(Set<Vertex>::count)
    .take(3)
    .productOf(Set<Vertex>::count)

fun List<Vertex>.part2(): Long = findClosestPairs()
    .findLastConnection()
    .let { (a, b) -> a.x * b.x }

fun List<Vertex>.findClosestPairs(limit: Int? = null): List<Pair<Vertex, Vertex>> {
    val queue = PriorityQueue(compareByDescending(Pair<Vertex, Vertex>::distance))

    forEachIndexed { index, vertex ->
        drop(index + 1).forEach { otherVertex ->
            val pair = minOf(vertex, otherVertex) to maxOf(vertex, otherVertex)

            if (limit == null || queue.size < limit) {
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

fun List<Pair<Vertex, Vertex>>.findLastConnection(): Pair<Vertex, Vertex> {
    val disjointSet = DisjointSet(flatMap { pair -> pair.toList().toSet() })

    forEach { pair ->
        disjointSet.union(pair.first, pair.second)
        if (disjointSet.components.count() == 1) return pair
    }

    throw IllegalStateException("No final connection found")
}
