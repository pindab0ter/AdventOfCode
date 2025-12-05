package nl.pindab0ter.aoc2025.day03

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

@DisplayName("2025 Day 03 - Lobby")
class LobbyKtTest {
    @ParameterizedTest(name = "{1} → {0}")
    @MethodSource("partOneProvider")
    fun `Part one`(expected: JoltageRating, bank: Bank) {
        val actual = bank.getJoltage()

        assertEquals(expected, actual)
    }

    // @ParameterizedTest(name = "{1} → {0}")
    @MethodSource("partTwoProvider")
    fun `Part two`(expected: String, input: String) {
        val actual = TODO()

        assertEquals(expected, actual)
    }

    companion object {
        @JvmStatic
        fun partOneProvider(): Stream<Arguments> = Stream.of(
            arguments(98, listOf(9, 8, 7, 6, 5, 4, 3, 2, 1, 1, 1, 1, 1, 1, 1)),
            arguments(89, listOf(8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9)),
            arguments(78, listOf(2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 7, 8)),
            arguments(92, listOf(8, 1, 8, 1, 8, 1, 9, 1, 1, 1, 1, 2, 1, 1, 1)),
        )

        @JvmStatic
        fun partTwoProvider(): Stream<Arguments> = Stream.of(
            arguments("expected", "actual"),
        )
    }
}
