package nl.pindab0ter.aoc2023.day06

import nl.pindab0ter.lib.assertAllEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("2023 Day 06 - Wait For It")
class WaitForItKtTest {

    @Test
    fun `First race`() {
        val game = Game.partOne(input).first()

        assertAllEquals(
            0L to game.play(0),
            6L to game.play(1),
            10L to game.play(2),
            12L to game.play(3),
            12L to game.play(4),
            10L to game.play(5),
            6L to game.play(6),
            0L to game.play(7),
        )
    }

    @Test
    fun `Calculate ways to win`() {
        val games = Game.partOne(input)
        assertAllEquals(
            4 to games[0].waysToWin(),
            8 to games[1].waysToWin(),
            9 to games[2].waysToWin(),
        )
    }

    companion object {
        val input = """
            Time:      7  15   30
            Distance:  9  40  200
        """.trimIndent()
    }
}
