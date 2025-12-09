package nl.pindab0ter.aoc2025.day07

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import java.util.stream.Stream
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@DisplayName("2025 Day 07 - Laboratories")
class LaboratoriesKtTest {
    @Test
    fun `Part one`() {
        val actual = """
            .......S.......
            ...............
            .......^.......
            ...............
            ......^.^......
            ...............
            .....^.^.^.....
            ...............
            ....^.^...^....
            ...............
            ...^.^...^.^...
            ...............
            ..^...^.....^..
            ...............
            .^.^.^.^.^...^.
            ...............
            """
            .trimIndent()
            .lines()
            .simulateTachyonBeamSplitting()
            .count

        assertEquals(21, actual)
    }

    @Test
    @Ignore
    fun `Part two`() {
        val expected = TODO()
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
