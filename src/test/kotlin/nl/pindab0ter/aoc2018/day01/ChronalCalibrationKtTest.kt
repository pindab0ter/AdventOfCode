package nl.pindab0ter.aoc2018.day01

import nl.pindab0ter.lib.assertAllEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("2018 Day 01 - Chronal Calibration")
internal class ChronalCalibrationKtTest {
    @Test
    fun findFinalFrequency() = assertAllEquals(
        3 to findFinalFrequency(listOf(1, -2, 3, 1)),
        3 to findFinalFrequency(listOf(1, 1, 1)),
        0 to findFinalFrequency(listOf(1, 1, -2)),
        -6 to findFinalFrequency(listOf(-1, -2, -3)),
    )

    @Test
    fun findFirstRepeatedFrequency() = assertAllEquals(
        0 to findFirstRepeatedFrequency(listOf(1, -1)),
        10 to findFirstRepeatedFrequency(listOf(3, 3, 4, -2, -4)),
        5 to findFirstRepeatedFrequency(listOf(-6, 3, 8, 5, -6)),
        14 to findFirstRepeatedFrequency(listOf(7, 7, -2, -7, -4)),
    )
}
