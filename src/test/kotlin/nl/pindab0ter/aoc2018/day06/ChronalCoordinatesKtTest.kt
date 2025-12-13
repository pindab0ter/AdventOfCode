package nl.pindab0ter.aoc2018.day06

import nl.pindab0ter.lib.types.Point
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("2018 Day 06 - Chronal Coordinates")
class ChronalCoordinatesKtTest {

    private val input = listOf(
        Point(1, 1),
        Point(1, 6),
        Point(8, 3),
        Point(3, 4),
        Point(5, 5),
        Point(8, 9)
    )

    @Test
    fun largestArea() {
        val actual = PointGrid(input).largestArea()
        Assertions.assertEquals(17, actual)
    }

    @Test
    fun sizeOfSafestPointWithin() {
        val actual = PointGrid(input).sizeOfSafestPointWithin(32)
        Assertions.assertEquals(16, actual)
    }
}
