package nl.pindab0ter.lib

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

@DisplayName("Collections")
class CollectionsKtTest {

    @Nested
    inner class Iterable {

        @Nested
        @DisplayName("allElementsEqual")
        inner class AllElementsEqual {
            @Test
            fun `Lists of integers`() = assertAllEquals(
                true to listOf(1, 1).allElementsEqual(),
                false to listOf(0, 1).allElementsEqual(),
            )

            @Test
            fun `Lists of lists of integers`() = assertAllEquals(
                true to listOf(listOf(1), listOf(1)).allElementsEqual(),
                false to listOf(listOf(0), listOf(1)).allElementsEqual(),
            )

            @Test
            fun `Lists of pairs of integers`() = assertAllEquals(
                true to listOf(0 to 1, 0 to 1).allElementsEqual(),
                false to listOf(0 to 0, 0 to 1).allElementsEqual(),
            )
        }
    }

    @Nested
    @DisplayName("Two-dimensional iterable")
    inner class TwoDimensionalArray {
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
    }
}
