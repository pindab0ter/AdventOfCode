package nl.pindab0ter.aoc2025.day05

import org.junit.jupiter.api.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals

@DisplayName("2025 Day 05 - Cafeteria")
class CafeteriaKtTest {
    @Test
    fun `Part one`() {
        val (freshRanges, ingredients) = """
            3-5
            10-14
            16-20
            12-18

            1
            5
            8
            11
            17
            32
            """
            .trimIndent()
            .parse()

        val actual = ingredients.filterSpoiled(freshRanges).count()

        assertEquals(3, actual)
    }

    @Test
    fun `Part two`() {
        val (ranges, _) = """
            3-5
            10-14
            16-20
            12-18

            1
            5
            8
            11
            17
            32
            """
            .trimIndent()
            .parse()

        assertEquals(14uL, ranges.countUniqueIds())

        val (rangesWithCurveball, _) = """
            3-5
            10-14
            16-20
            12-18
            9-21

            1
            5
            8
            11
            17
            32
            """
            .trimIndent()
            .parse()

        assertEquals(16uL, rangesWithCurveball.countUniqueIds())
    }
}
