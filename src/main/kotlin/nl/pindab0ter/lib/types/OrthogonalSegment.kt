package nl.pindab0ter.lib.types

data class OrthogonalSegment(val start: Point, val end: Point) {
    val highestPoint by lazy { if (start.y <= end.y) start else end }
    val lowestPoint by lazy { if (start.y > end.y) start else end }
    val leftMostPoint by lazy { if (start.x <= end.x) start else end }
    val rightMostPoint by lazy { if (start.x > end.x) start else end }
    val isHorizontal by lazy { start.y == end.y }
    val isVertical by lazy { start.x == end.x }
    val y by lazy { if (isHorizontal) start.y else null }
    val x by lazy { if (isVertical) start.x else null }
    val verticalRange by lazy { highestPoint.y..lowestPoint.y }
    val horizontalExclusiveRange by lazy { leftMostPoint.x + 1..<rightMostPoint.x }
    val verticalExclusiveRange by lazy { highestPoint.y + 1..<lowestPoint.y }
    val direction by lazy {
        when {
            start.y > end.y -> Direction.NORTH
            start.x < end.x -> Direction.EAST
            start.y < end.y -> Direction.SOUTH
            start.x > end.x -> Direction.WEST
            else -> throw IllegalStateException("Segment does not have a direction")
        }
    }

    operator fun contains(point: Point): Boolean =
        (isVertical && point.x == x && point.y in highestPoint.y..lowestPoint.y) ||
                (isHorizontal && point.y == y && point.x in leftMostPoint.x..rightMostPoint.x)

    override fun toString(): String = "Segment(${start.x},${start.y} $direction ${end.x},${end.y})"
}
