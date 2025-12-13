package nl.pindab0ter.aoc2025.day09

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals
import kotlin.test.Ignore
import kotlin.test.Test

@DisplayName("2025 Day 09 - Movie Theater")
class MovieTheaterKtTest {
    val exampleInput = """
        7,1
        11,1
        11,7
        9,7
        9,5
        2,5
        2,3
        7,3"""
        .trimIndent()
        .parse()

    @Test
    fun `Part one`() {
        val actual = exampleInput.findGreatestSurface()

        assertEquals(50L, actual)
    }

    @Test
    @Ignore
    fun `Part two`() {
        val actual = TODO()
        val expected = TODO()

        assertEquals(expected, actual)
    }
}
