package nl.pindab0ter.aoc2024.day06

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

@DisplayName("2024 Day 06 - Guard Gallivant")
class GuardGallivantKtTest {
    @ParameterizedTest(name = "{1} → {0}")
    @MethodSource("partOneProvider")
    fun `Part one`(expected: String, input: String) {
        val actual = TODO()

        assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "{1} → {0}")
    @MethodSource("partTwoProvider")
    fun `Part two`(expected: String, input: String) {
        val actual = TODO()

        assertEquals(expected, actual)
    }

    companion object {
        @JvmStatic
        fun partOneProvider(): Stream<Arguments> = Stream.of(
             arguments("expected", "actual"),
        )

        @JvmStatic
        fun partTwoProvider(): Stream<Arguments> = Stream.of(
             arguments("expected", "actual"),
        )
    }
}
