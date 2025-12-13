package nl.pindab0ter.lib.collections

import nl.pindab0ter.lib.assertAllEquals
import nl.pindab0ter.lib.types.Coordinate
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

class MatrixKtTest {
    @ParameterizedTest(name = "{1} → {0}")
    @MethodSource("clockwiseRotationListProvider")
    fun `Rotate 2D lists 45º`(expected: List<List<Int>>, input: List<List<Int>>) {
        assertEquals(expected, input.rotate45Degrees())
    }

    @ParameterizedTest(name = "{1} → {0}")
    @MethodSource("antiClockwiseRotationListProvider")
    fun `Rotate 2D lists 45º counter clockwise`(expected: List<List<Int>>, input: List<List<Int>>) {
        assertEquals(expected, input.rotate45DegreesAntiClockwise())
    }

    @Nested
    @DisplayName("coordinatesOfFirst")
    inner class CoordinateOfFirst {
        @Test
        fun `Returns the coordinates of the first match`() = assertAllEquals(
            Coordinate(0, 0) to listOf(
                listOf(1)
            ).coordinateOfFirst { it == 1 },
            Coordinate(1, 1) to listOf(
                listOf(0, 0),
                listOf(0, 1)
            ).coordinateOfFirst { it == 1 },
            Coordinate(0, 2) to listOf(
                listOf(0), listOf(0), listOf(1)
            ).coordinateOfFirst { it == 1 },
            Coordinate(2, 0) to listOf(
                listOf(0, 0, 1)
            ).coordinateOfFirst { it == 1 },
            null to listOf(
                listOf(0)
            ).coordinateOfFirst { it == 1 },
        )
    }

    @Nested
    @DisplayName("find")
    inner class Find {
        @Test
        fun `Returns the first element matching the predicate, or null`() = assertAllEquals(
            1 to listOf(listOf(1)).find { it == 1 },
            1 to listOf(listOf(0, 0, 0), listOf(0, 0, 1)).find { it == 1 },
            null to listOf(listOf(0)).find { it == 1 },
        )
    }

    @Nested
    @DisplayName("transpose")
    inner class Transpose {
        @Test
        fun `Returns the transposed matrix`() {
            assertEquals(
                expected = listOf(
                    listOf(1, 1),
                    listOf(2, 2)
                ),
                actual = listOf(
                    listOf(1, 2),
                    listOf(1, 2)
                ).transpose()
            )

            assertEquals(
                expected = listOf(
                    listOf(1, 2, 3),
                    listOf(1, 2, 3)
                ),
                actual = listOf(
                    listOf(1, 1),
                    listOf(2, 2),
                    listOf(3, 3)
                ).transpose()
            )

            assertEquals(
                expected = listOf(
                    listOf(1, 2, 3),
                    listOf(1, 2, 3)
                ),
                actual = listOf(
                    listOf(1, 1),
                    listOf(2, 2),
                    listOf(3, 3)
                ).transpose()
            )

            assertEquals(
                expected = listOf(),
                actual = listOf<List<Int>>().transpose()
            )
        }

        @Test
        fun `Throws an exception when the rows are not all the same length`() {
            assertThrows<IllegalArgumentException> {
                listOf(listOf(1), listOf(1, 2)).transpose()
            }
        }
    }

    companion object {
        @JvmStatic
        fun clockwiseRotationListProvider(): Stream<Arguments> = Stream.of(
            Arguments.arguments(
                listOf(
                    listOf(1),
                    listOf(4, 2),
                    listOf(7, 5, 3),
                    listOf(8, 6),
                    listOf(9),
                ),
                listOf(
                    listOf(1, 2, 3),
                    listOf(4, 5, 6),
                    listOf(7, 8, 9),
                )
            ),
            Arguments.arguments(
                listOf(
                    listOf(1),
                    listOf(3, 2),
                    listOf(5, 4),
                    listOf(7, 6),
                    listOf(8),
                ),
                listOf(
                    listOf(1, 2),
                    listOf(3, 4),
                    listOf(5, 6),
                    listOf(7, 8),
                )
            ),
            Arguments.arguments(
                listOf(
                    listOf(1),
                    listOf(5, 2),
                    listOf(6, 3),
                    listOf(7, 4),
                    listOf(8),
                ),
                listOf(
                    listOf(1, 2, 3, 4),
                    listOf(5, 6, 7, 8),
                )
            ),
        )

        @JvmStatic
        fun antiClockwiseRotationListProvider(): Stream<Arguments> = Stream.of(
            Arguments.arguments(
                listOf(
                    listOf(3),
                    listOf(6, 2),
                    listOf(9, 5, 1),
                    listOf(8, 4),
                    listOf(7),
                ),
                listOf(
                    listOf(1, 2, 3),
                    listOf(4, 5, 6),
                    listOf(7, 8, 9),
                )
            ),
            Arguments.arguments(
                listOf(
                    listOf(2),
                    listOf(4, 1),
                    listOf(6, 3),
                    listOf(8, 5),
                    listOf(7),
                ),
                listOf(
                    listOf(1, 2),
                    listOf(3, 4),
                    listOf(5, 6),
                    listOf(7, 8),
                )
            ),
            Arguments.arguments(
                listOf(
                    listOf(4),
                    listOf(8, 3),
                    listOf(7, 2),
                    listOf(6, 1),
                    listOf(5),
                ),
                listOf(
                    listOf(1, 2, 3, 4),
                    listOf(5, 6, 7, 8),
                )
            ),
        )
    }
}
