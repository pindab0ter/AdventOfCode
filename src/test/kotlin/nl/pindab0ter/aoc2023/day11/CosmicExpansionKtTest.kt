package nl.pindab0ter.aoc2023.day11

import nl.pindab0ter.lib.assertAllEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("2023 Day 11 - Cosmic Expansion")
class CosmicExpansionKtTest {

    @Test
    fun `Calculate sum of distances between all galaxies`() {
        val universe = parse(
            """
            ...#......
            .......#..
            #.........
            ..........
            ......#...
            .#........
            .........#
            ..........
            .......#..
            #...#.....
        """.trimIndent()
        )

        assertAllEquals(
            374L to universe.sumOfDistancesBetweenGalaxies(1),
            1030L to universe.sumOfDistancesBetweenGalaxies(10),
            8410L to universe.sumOfDistancesBetweenGalaxies(100),
        )
    }

}
