package nl.pindab0ter.aoc2025.day04

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@DisplayName("2025 Day 04 - Printing Department")
class PrintingDepartmentKtTest {
    @Test
    fun `Part one`() {
        val actual = """
            ..@@.@@@@.
            @@@.@.@.@@
            @@@@@.@.@@
            @.@@@@..@.
            @@.@@@@.@@
            .@@@@@@@.@
            .@.@.@.@@@
            @.@@@.@@@@
            .@@@@@@@@.
            @.@.@@@.@.
            """
            .trimIndent()
            .parse()
            .accessibleRollsMarked()
            .count { it == 'x' }

        assertEquals(13, actual)
    }

    @Test
    fun `Part two`() {
        val input = """
            ..@@.@@@@.
            @@@.@.@.@@
            @@@@@.@.@@
            @.@@@@..@.
            @@.@@@@.@@
            .@@@@@@@.@
            .@.@.@.@@@
            @.@@@.@@@@
            .@@@@@@@@.
            @.@.@@@.@.
            """
            .trimIndent()
            .parse()

        val actual = input.countRolls() - input.removeAccessibleRolls().countRolls()

        assertEquals(43, actual)
    }
}
