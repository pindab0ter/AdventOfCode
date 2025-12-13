package nl.pindab0ter.lib.collections

import nl.pindab0ter.lib.types.Coordinate
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
        listOf(1, 2, 3, 4),
        listOf(5, 6, 7, 8),
        listOf(9, 10, 11, 12),
    )

    @Test
    fun dimensions() {
        assertEquals(4, grid.width)
        assertEquals(3, grid.height)
    }

    @ParameterizedTest(name = "{1} → {0}")
    @MethodSource("neighboursProvider")
    fun neighbours(expected: List<Int>, coordinate: Coordinate) {
        assertEquals(expected, grid.neighbours(coordinate))
    }

    @Test
    fun mapIndexed() {
        val expected = listOf(
            listOf(Coordinate(0, 0) to 1, Coordinate(1, 0) to 2, Coordinate(2, 0) to 3, Coordinate(3, 0) to 4),
            listOf(Coordinate(0, 1) to 5, Coordinate(1, 1) to 6, Coordinate(2, 1) to 7, Coordinate(3, 1) to 8),
            listOf(Coordinate(0, 2) to 9, Coordinate(1, 2) to 10, Coordinate(2, 2) to 11, Coordinate(3, 2) to 12),
        ).toGrid()

        val actual = grid.mapIndexed { coordinate, value -> coordinate to value }

        assertContentEquals(expected, actual)
    }

    companion object {
        @JvmStatic
        fun neighboursProvider(): Stream<Arguments> = Stream.of(
            arguments(listOf(2, 5, 6), Coordinate(0, 0)),
            arguments(listOf(1, 3, 5, 6, 7), Coordinate(1, 0)),
            arguments(listOf(2, 4, 6, 7, 8), Coordinate(2, 0)),
            arguments(listOf(3, 7, 8), Coordinate(3, 0)),
            arguments(listOf(1, 2, 6, 9, 10), Coordinate(0, 1)),
            arguments(listOf(1, 2, 3, 5, 7, 9, 10, 11), Coordinate(1, 1)),
            arguments(listOf(2, 3, 4, 6, 8, 10, 11, 12), Coordinate(2, 1)),
            arguments(listOf(3, 4, 7, 11, 12), Coordinate(3, 1)),
            arguments(listOf(5, 6, 10), Coordinate(0, 2)),
            arguments(listOf(5, 6, 7, 9, 11), Coordinate(1, 2)),
            arguments(listOf(6, 7, 8, 10, 12), Coordinate(2, 2)),
            arguments(listOf(7, 8, 11), Coordinate(3, 2)),
        )
    }
}
