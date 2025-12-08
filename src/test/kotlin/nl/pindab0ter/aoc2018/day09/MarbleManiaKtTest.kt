package nl.pindab0ter.aoc2018.day09

import nl.pindab0ter.lib.assertAllEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("2018 Day 09 - Marble Mania")
class MarbleManiaKtTest {

    @Test
    fun `Calculate the high score`() = assertAllEquals(
        32L to MarbleMania(9, 25).play(),
        8317L to MarbleMania(10, 1618).play(),
        146373L to MarbleMania(13, 7999).play(),
        2764L to MarbleMania(17, 1104).play(),
        54718L to MarbleMania(21, 6111).play(),
        37305L to MarbleMania(30, 5807).play(),
    )

}
