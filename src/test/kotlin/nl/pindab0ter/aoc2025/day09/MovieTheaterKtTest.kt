package nl.pindab0ter.aoc2025.day09

import org.junit.jupiter.api.DisplayName
import kotlin.test.assertEquals
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
        val actual = exampleInput.findLargestRectangle().surface

        assertEquals(50L, actual)
    }

    @Test
    fun `Part two`() {
        val actual = exampleInput.findLargestValidRectangle().surface

        assertEquals(24L, actual)
    }
}
