package nl.pindab0ter.lib.types

data class OrthogonalPolygon(val vertices: List<Point>) {
    constructor(vararg vertices: Point) : this(vertices.toList())

    data class BoundingBox(val minX: Long, val minY: Long, val maxX: Long, val maxY: Long) {
        operator fun contains(point: Point): Boolean = point.x in minX..maxX && point.y in minY..maxY
    }

    val segments: List<OrthogonalSegment> = vertices.plus(vertices.first()).zipWithNext().map { OrthogonalSegment(it.first, it.second) }
    val boundingBox
        get() = BoundingBox(
            vertices.minOf(Point::x),
            vertices.minOf(Point::y),
            vertices.maxOf(Point::x),
            vertices.maxOf(Point::y)
        )

    infix fun intersectsWith(rectangle: Rectangle): Boolean = rectangle.segments.any { this.intersectsWith(it) }
    infix fun intersectsWith(other: OrthogonalSegment): Boolean = segments.any {
        // Parallel segments cannot intersect
        if (it.isHorizontal == other.isHorizontal) return@any false

        if (it.isVertical) {
            val isInXBounds = it.x in other.horizontalExclusiveRange
            val isInYBounds = other.y in it.verticalExclusiveRange

            return@any isInXBounds && isInYBounds
        } else {
            val isInXBounds = other.x in it.horizontalExclusiveRange
            val isInYBounds = it.y in other.verticalExclusiveRange

            return@any isInYBounds && isInXBounds
        }
    }

    operator fun contains(rectangle: Rectangle): Boolean = rectangle.points.all { this.contains(it) }
    operator fun contains(point: Point): Boolean {
        if (point !in boundingBox) return false

        // Points on the edge are considered to be in bounds
        if (segments.any { point in it }) return true

        val verticalSegmentsWithPreviousAndNextSegment =
            sequenceOf(segments.last()).plus(segments).plus(segments.first())
                .windowed(3)
                .filter { (_, segment, _) -> segment.isVertical }

        return verticalSegmentsWithPreviousAndNextSegment.fold(false) { isInside, (previousSegment, verticalSegment, nextSegment) ->
            // Disregard the y-coordinate that connects to a counter-clockwise corner
            if (verticalSegment.direction.ninetyDegreesCounterClockwise() == nextSegment.direction && verticalSegment.end.y == point.y) return@fold isInside
            if (previousSegment.direction.ninetyDegreesCounterClockwise() == verticalSegment.direction && verticalSegment.start.y == point.y) return@fold isInside

            return@fold when (point.y) {
                in verticalSegment.verticalRange if point.x < verticalSegment.start.x -> !isInside
                else -> isInside
            }
        }
    }
}
