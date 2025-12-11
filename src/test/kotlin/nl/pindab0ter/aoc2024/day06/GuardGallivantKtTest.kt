package nl.pindab0ter.aoc2024.day06

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals

@DisplayName("2024 Day 06 - Guard Gallivant")
class GuardGallivantKtTest {
    @Test
    fun `Count amount of tiles the guard has visited`() = runBlocking {
        val lab = Lab.fromString(
            """
            ....#.....
            .........#
            ..........
            ..#.......
            .......#..
            ..........
            .#..^.....
            ........#.
            #.........
            ......#...
            """.trimIndent()
        )

        lab.gallivant()

        assertEquals(41, lab.visitedTiles())
    }
}
