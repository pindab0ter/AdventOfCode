package nl.pindab0ter.aoc2023.day10

import nl.pindab0ter.lib.assertAllEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("2023 Day 10 - Pipe Maze")
class PipeMazeKtTest {
    @Test
    fun `Furthest distance from the starting point`() = assertAllEquals(
        4 to Maze.from(
            """
                .....
                .S-7.
                .|.|.
                .L-J.
                .....
            """.trimIndent()
        ).furthestDistanceFromStart,
        8 to Maze.from(
            """
                7-F7-
                .FJ|7
                SJLL7
                |F--J
                LJ.LJ
            """.trimIndent()
        ).furthestDistanceFromStart,
    )

    @Test
    fun `Amount of tiles enclosed by the loop`() = assertAllEquals(
        1 to Maze.from(
            """
                .....
                .S-7.
                .|.|.
                .L-J.
                ..... 
            """.trimIndent()
        ).tilesEnclosedByLoop.count(),
        4 to Maze.from(
            """
                ...........
                .S-------7.
                .|F-----7|.
                .||.....||.
                .||.....||.
                .|L-7.F-J|.
                .|..|.|..|.
                .L--J.L--J.
                ...........
            """.trimIndent()
        ).tilesEnclosedByLoop.count(),
        8 to Maze.from(
            """
                .F----7F7F7F7F-7....
                .|F--7||||||||FJ....
                .||.FJ||||||||L7....
                FJL7L7LJLJ||LJ.L-7..
                L--J.L7...LJS7F-7L7.
                ....F-J..F7FJ|L7L7L7
                ....L7.F7||L7|.L7L7|
                .....|FJLJ|FJ|F7|.LJ
                ....FJL-7.||.||||...
                ....L---J.LJ.LJLJ...
            """.trimIndent()
        ).tilesEnclosedByLoop.count(),
        10 to Maze.from(
            """
                FF7FSF7F7F7F7F7F---7
                L|LJ||||||||||||F--J
                FL-7LJLJ||||||LJL-77
                F--JF--7||LJLJ7F7FJ-
                L---JF-JLJ.||-FJLJJ7
                |F|F-JF---7F7-L7L|7|
                |FFJF7L7F-JF7|JL---7
                7-L-JL7||F7|L7F-7F7|
                L.L7LFJ|||||FJL7||LJ
                L7JLJL-JLJLJL--JLJ.L
            """.trimIndent()
        ).tilesEnclosedByLoop.count()
    )
}
