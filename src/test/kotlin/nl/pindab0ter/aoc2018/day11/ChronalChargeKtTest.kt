package nl.pindab0ter.aoc2018.day11

import nl.pindab0ter.lib.assertAllEquals
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test

@DisplayName("2018 Day 11 - Chronal Charge")
class ChronalChargeKtTest {

    @Test
    fun `Grids are calculated correctly`() = assertAllEquals(
        4 to Grid(8).cells[3][5],
        -5 to Grid(57).cells[122][79],
        0 to Grid(39).cells[217][196],
        4 to Grid(71).cells[101][153],
    )

    @Test
    fun `Determine the power level of a 3x3 square`() = assertAllEquals(
        29 to Grid(18).powerLevelFor(33, 45, 3),
        30 to Grid(42).powerLevelFor(21, 61, 3),
    )

    @Test
    fun `Find the most powerful square`() = assertAllEquals(
        29 to Grid(18).findMostPowerfulSquare(3).powerLevel,
        30 to Grid(42).findMostPowerfulSquare(3).powerLevel,
        113 to Grid(18).findMostPowerfulSquare(16).powerLevel,
        119 to Grid(42).findMostPowerfulSquare(12).powerLevel,
    )

    @Test
    fun `Find the most powerful square of any size`() = assertAllEquals(
        Square(90, 269, 16, 113) to Grid(18).findMostPowerfulSquareOfAnySize(),
        Square(232, 251, 12, 119) to Grid(42).findMostPowerfulSquareOfAnySize(),
    )
}
