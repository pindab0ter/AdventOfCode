package nl.pindab0ter.lib

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class GridTest {
    val grid = Grid(
        listOf(1, 2, 3),
        listOf(4, 5, 6),
        listOf(7, 8, 9),
    )

    @ParameterizedTest(name = "{1} → {0}")
    @MethodSource("partOneProvider")
    fun neighbours(expected: List<Int>, coordinate: Coordinate) {
        assertEquals(expected, grid.neighbours(coordinate))
    }

    @Test
    fun mapIndexed() {
        val expected = listOf(
            listOf(Coordinate(0, 0) to 1, Coordinate(1, 0) to 2, Coordinate(2, 0) to 3),
            listOf(Coordinate(0, 1) to 4, Coordinate(1, 1) to 5, Coordinate(2, 1) to 6),
            listOf(Coordinate(0, 2) to 7, Coordinate(1, 2) to 8, Coordinate(2, 2) to 9),
        ).toGrid()

        val actual = grid.mapIndexed { coordinate, value -> coordinate to value }

        assertContentEquals(expected, actual)
    }

    companion object {
        @JvmStatic
        fun partOneProvider(): Stream<Arguments> = Stream.of(
            arguments(listOf(2, 4, 5), Coordinate(0, 0)),
            arguments(listOf(1, 3, 4, 5, 6), Coordinate(1, 0)),
            arguments(listOf(2, 5, 6), Coordinate(2, 0)),
            arguments(listOf(1, 2, 5, 7, 8), Coordinate(0, 1)),
            arguments(listOf(1, 2, 3, 4, 6, 7, 8, 9), Coordinate(1, 1)),
            arguments(listOf(2, 3, 5, 8, 9), Coordinate(2, 1)),
            arguments(listOf(4, 5, 8), Coordinate(0, 2)),
            arguments(listOf(4, 5, 6, 7, 9), Coordinate(1, 2)),
            arguments(listOf(5, 6, 8), Coordinate(2, 2)),
        )
    }
}
