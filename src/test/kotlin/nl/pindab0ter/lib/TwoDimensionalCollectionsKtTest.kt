package nl.pindab0ter.lib

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

class TwoDimensionalCollectionsKtTest {
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

    companion object {
        @JvmStatic
        fun clockwiseRotationListProvider(): Stream<Arguments> = Stream.of(
            arguments(
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
            arguments(
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
            arguments(
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
            arguments(
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
            arguments(
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
            arguments(
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
