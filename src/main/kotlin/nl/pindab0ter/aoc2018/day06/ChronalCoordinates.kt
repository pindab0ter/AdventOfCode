package nl.pindab0ter.aoc2018.day06

import nl.pindab0ter.aoc.getInput
import java.util.*
import kotlin.math.abs

fun main() = getInput(2018, 6)
    .lines()
    .map { it.split(", ").map(String::toInt) }
    .map { (x, y) -> Point(x, y) }
    .let(::PointGrid)
    .let { grid ->
        print(
            """
            --- Day 6: Chronal Coordinates ---

            Part one: What is the size of the largest area that isn't infinite?
            ${grid.largestArea()}

            Part two: What is the size of the region containing all locations which have a total distance to all given coordinates of less than 10000?
            ${grid.sizeOfSafestPointWithin(10_000)}

            """.trimIndent()
        )
    }

data class Point(val x: Int, val y: Int) {
    fun distanceTo(x: Int, y: Int) = abs(this.x - x) + abs(this.y - y)
}

data class PointGrid(val points: List<Point>) {
    private val x: Int = points.minBy { it.x }.x
    private val y: Int = points.minBy { it.y }.y
    private val width: Int = points.maxBy { it.x }.x
    private val height: Int = points.maxBy { it.y }.y

    fun largestArea(): Int = flatMapOverGrid { x, y ->
        points.map { point -> point.distanceTo(x, y) to point }              // Calculate the distance for each Point
            .groupBy({ (distance, _) -> distance }, { (_, point) -> point }) // Group by distance
            .toSortedMap().first()                                           // Take the closest
            .let { if (it?.size == 1) it.first() else null }                 // Take it unless there's more than one
    }
        .filterNotNull()
        .filterNot { it.x in listOf(x, width) || it.y in listOf(y, height) }     // Remove any Points on an edge
        .groupingBy { it }                                                       // Group by Point
        .eachCount().values.max()                                              // Get the group with the highest count

    fun sizeOfSafestPointWithin(maxDistance: Int): Int = flatMapOverGrid { x, y ->
        points.sumOf { it.distanceTo(x, y) }                             // Sum of the distance to each Point
    }.count { it < maxDistance }                                             // Count how many are within max distance

    private fun <T> flatMapOverGrid(transform: (Int, Int) -> T): List<T> = (x until width).flatMap { x ->
        (y until height).map { y ->
            transform(x, y)
        }
    }
}

fun <K, V> SortedMap<K, V>.first(): V? = get(firstKey())
