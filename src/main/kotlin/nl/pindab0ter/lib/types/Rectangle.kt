package nl.pindab0ter.lib.types

import clojure.lang.Numbers
import org.openrndr.math.Vector2

data class Rectangle(val a: Point, val b: Point) {
    constructor(pair: Pair<Point, Point>) : this(pair.first, pair.second)

    val surface: Long by lazy { (Numbers.abs(a.x - b.x) + 1) * (Numbers.abs(a.y - b.y) + 1) }
    val points: List<Point> by lazy {
        listOf(
            Point(minOf(a.x, b.x), minOf(a.y, b.y)), // Top left
            Point(maxOf(a.x, b.x), minOf(a.y, b.y)), // Top right
            Point(maxOf(a.x, b.x), maxOf(a.y, b.y)), // Bottom right
            Point(minOf(a.x, b.x), maxOf(a.y, b.y)), // Bottom left
        )
    }
    val segments: List<OrthogonalSegment> =
        points.plus(points.first()).zipWithNext().map { (a, b) -> OrthogonalSegment(a, b) }
    val vectors: List<Vector2> by lazy { points.map { Vector2(it.x.toDouble(), it.y.toDouble()) } }
}
