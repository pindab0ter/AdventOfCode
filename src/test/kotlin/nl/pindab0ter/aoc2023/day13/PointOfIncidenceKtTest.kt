package nl.pindab0ter.aoc2023.day13

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.*
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@DisplayName("2023 Day 13 - Point of Incidence")
class PointOfIncidenceKtTest {
    @ParameterizedTest
    @MethodSource("verticalReflectionLineProvider")
    fun `Find vertical reflection lines`(expected: Int?, input: String) {
        val pattern = parse(input)[0]

        assertEquals(expected, locationOfVerticalReflectionLine(pattern.columns()))
    }

    @ParameterizedTest
    @MethodSource("horizontalReflectionLineProvider")
    fun `Find horizontal reflection lines`(expected: Int?, input: String) {
        val pattern = parse(input)[0]

        assertEquals(expected, locationOfVerticalReflectionLine(pattern.rows()))
    }

    @Test
    fun `Calculate the summary of all notes`() {
        val input = """ #.##..##.
            ..#.##.#.
            ##......#
            ##......#
            ..#.##.#.
            ..##..##.
            #.#.##.#.

            #...##..#
            #....#..#
            ..##..###
            #####.##.
            #####.##.
            ..##..###
            #....#..#
        """.trimIndent()

        val patterns = parse(input)

        assertEquals(405, getSummaryOfAllNotes(patterns))
    }

    companion object {
        @JvmStatic
        fun verticalReflectionLineProvider(): Stream<Arguments> = Stream.of(
            arguments(
                5,
                """
                #.##..##.
                ..#.##.#.
                ##......#
                ##......#
                ..#.##.#.
                ..##..##.
                    #.#.##.#.""".trimIndent()
            ),
            arguments(
                null,
                """
                #...##..#
                #....#..#
                ..##..###
                #####.##.
                #####.##.
                ..##..###
                #....#..#
                """.trimIndent()
            ),
            arguments(
                11,
                """
                ..####..##..##..#
                ...#..##.####.##.
                .##.#.##..##..##.
                ...#..##.####.##.
                .##..#..#....#..#
                ##.##.##########.
                #########.##.####
                """.trimIndent()
            ),
        )

        @JvmStatic
        fun horizontalReflectionLineProvider(): Stream<Arguments> = Stream.of(
            arguments(
                null,
                """
                #.##..##.
                ..#.##.#.
                ##......#
                ##......#
                ..#.##.#.
                ..##..##.
                    #.#.##.#.""".trimIndent()
            ),
            arguments(
                4,
                """
                #...##..#
                #....#..#
                ..##..###
                #####.##.
                #####.##.
                ..##..###
                #....#..#
                """.trimIndent()
            ),
            arguments(
                null,
                """
                ..####..##..##..#
                ...#..##.####.##.
                .##.#.##..##..##.
                ...#..##.####.##.
                .##..#..#....#..#
                ##.##.##########.
                #########.##.####
                """.trimIndent()
            ),
        )
    }
}
