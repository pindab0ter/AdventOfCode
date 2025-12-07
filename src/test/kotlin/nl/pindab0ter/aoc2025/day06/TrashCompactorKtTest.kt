package nl.pindab0ter.aoc2025.day06

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

@DisplayName("2025 Day 06 - Trash Compactor")
class TrashCompactorKtTest {
    @ParameterizedTest(name = "{1} → {0}")
    @MethodSource("partOneProvider")
    fun `Part one`(expected: String, input: List<String>) {
        val actual = Problem(input).solve()

        assertEquals(expected.toULong(), actual)
    }

    //    @ParameterizedTest(name = "{1} → {0}")
    @MethodSource("partTwoProvider")
    fun `Part two`(expected: String, input: String) {
        val actual = TODO()

        assertEquals(expected, actual)
    }

    companion object {
        @OptIn(ExperimentalUnsignedTypes::class)
        @JvmStatic
        fun partOneProvider(): Stream<Arguments> = Stream.of(
            arguments("33210", listOf("123", "45", "6", "*")),
            arguments("490", listOf("328", "64", "98", "+")),
            arguments("4243455", listOf("51", "387", "215", "*")),
            arguments("401", listOf("64", "23", "314", "+")),
        )

        @JvmStatic
        fun partTwoProvider(): Stream<Arguments> = Stream.of(
            arguments("expected", "actual"),
        )
    }
}
