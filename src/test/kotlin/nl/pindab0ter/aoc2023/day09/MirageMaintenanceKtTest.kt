package nl.pindab0ter.aoc2023.day09

import nl.pindab0ter.lib.assertAllEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("2023 Day 09 - Mirage Maintenance")
class MirageMaintenanceKtTest {
    @Test
    fun `Extrapolate the next number in the sequence`() = assertAllEquals(
        18 to extrapolateValue(listOf(0, 3, 6, 9, 12, 15)),
        28 to extrapolateValue(listOf(1, 3, 6, 10, 15, 21)),
        68 to extrapolateValue(listOf(10, 13, 16, 21, 30, 45)),
    )

    @Test
    fun `Extrapolate the previous number in the sequence`() = assertAllEquals(
        -3 to extrapolateValue(listOf(0, 3, 6, 9, 12, 15).asReversed()),
        0 to extrapolateValue(listOf(1, 3, 6, 10, 15, 21).asReversed()),
        5 to extrapolateValue(listOf(10, 13, 16, 21, 30, 45).asReversed()),
    )
}
