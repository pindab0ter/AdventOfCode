package nl.pindab0ter.aoc2025.day06

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.Test
import kotlin.test.assertEquals

@DisplayName("2025 Day 06 - Trash Compactor")
class TrashCompactorKtTest {
    @ParameterizedTest(name = "{1} → {0}")
    @MethodSource("partOneProvider")
    fun `Part one`(expected: String, input: List<String>) {
        val actual = Problem(input).solve()

        assertEquals(expected.toULong(), actual)
    }

    @Test
    fun `Part two`() {
        val actual = """
            123 328  51 64 
             45 64  387 23 
              6 98  215 314
            *   +   *   + 
            """
            .trimIndent()
            .cephalopodMathParse()

        val expected = listOf(
            Problem(listOf(4uL, 431uL, 623uL), plus),
            Problem(listOf(175uL, 581uL, 32uL), times),
            Problem(listOf(8uL, 248uL, 369uL), plus),
            Problem(listOf(356uL, 24uL, 1uL), times),
        )

        assertEquals(expected, actual)
        assertEquals(3263827uL, actual.sumOf(Problem::solve))
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
    }
}
