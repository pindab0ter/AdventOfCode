package nl.pindab0ter.aoc2018.day06

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.lib.types.Point
import java.util.*

fun main() = getInput(2018, 6)
    .lines()
    .map { it.split(", ").map(String::toLong) }
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

data class PointGrid(val points: List<Point>) {
    private val x: Long = points.minBy { it.x }.x
    private val y: Long = points.minBy { it.y }.y
    private val width: Long = points.maxBy { it.x }.x
    private val height: Long = points.maxBy { it.y }.y

    fun largestArea(): Int = flatMapOverGrid { x, y ->
        points.map { point -> point.manhattanDistanceTo(x, y) to point }     // Calculate the distance for each Point
            .groupBy({ (distance, _) -> distance }, { (_, point) -> point }) // Group by distance
            .toSortedMap().first()                                           // Take the closest
            .let { if (it?.size == 1) it.first() else null }                 // Take it unless there's more than one
    }
        .filterNotNull()
        .filterNot { it.x in listOf(x, width) || it.y in listOf(y, height) } // Remove any Points on an edge
        .groupingBy { it }                                                   // Group by Point
        .eachCount().values.max()                                            // Get the group with the highest count

    fun sizeOfSafestPointWithin(maxDistance: Long): Int =
        flatMapOverGrid { x, y ->
            points.sumOf { it.manhattanDistanceTo(x, y) }                    // Sum of the distance to each Point
        }.count { it < maxDistance }                                         // Count how many are within max distance

    private fun <T> flatMapOverGrid(transform: (Long, Long) -> T): List<T> = (x until width).flatMap { x ->
        (y until height).map { y ->
            transform(x, y)
        }
    }
}

fun <K, V> SortedMap<K, V>.first(): V? = get(firstKey())
