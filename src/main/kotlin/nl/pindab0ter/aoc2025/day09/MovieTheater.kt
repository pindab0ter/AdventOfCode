package nl.pindab0ter.aoc2025.day09

import clojure.lang.Numbers.abs
import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.lib.collections.combinations
import nl.pindab0ter.lib.println
import nl.pindab0ter.lib.types.Point
import kotlin.math.max

fun main() {
     val grid = getInput(2025, 9).parse()

    val greatestPossibleSurface = grid.findGreatestSurface()
    println("The greatest surface you can make with any two points is: $greatestPossibleSurface")
}

fun List<Point>.findGreatestSurface(): Long = combinations()
    .fold(0L) { acc, pair ->
        max(acc, pair.surface())
    }

fun Pair<Point, Point>.surface(): Long = (abs(first.x - second.x) + 1) * (abs(first.y - second.y) + 1)

fun String.parse(): List<Point> = lines().map { line -> line
    .split(",")
    .map(String::toLong)
    .let { (x, y) -> Point(x, y) }
}
