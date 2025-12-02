package nl.pindab0ter.aoc2025.day02

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

@DisplayName("2025 Day 02 - Gift Shop")
class GiftShopKtTest {
    @ParameterizedTest(name = "{1} → {0}")
    @MethodSource("partOneProvider")
    fun `Part one`(expected: List<ULong>, input: ULongRange) {
        val actual = input.sequencesOfDigitsRepeatedTwice()

        assertContentEquals(expected, actual)
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
            arguments(listOf(11uL, 22uL), ULongRange(11uL, 22uL)),
            arguments(listOf(99uL), ULongRange(95uL, 115uL)),
            arguments(listOf(1010uL), ULongRange(998uL, 1012uL)),
            arguments(listOf(1188511885uL), ULongRange(1188511880uL, 1188511890uL)),
            arguments(listOf(222222uL), ULongRange(222220uL, 222224uL)),
            arguments(emptyList<UInt>(), ULongRange(1698522uL, 1698528uL)),
            arguments(listOf(446446uL), ULongRange(446443uL, 446449uL)),
            arguments(listOf(38593859uL), ULongRange(38593856uL, 38593862uL)),
        )

        @JvmStatic
        fun partTwoProvider(): Stream<Arguments> = Stream.of(
            arguments("expected", "actual"),
        )
    }
}
