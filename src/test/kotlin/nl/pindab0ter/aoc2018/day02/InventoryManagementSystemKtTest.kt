package nl.pindab0ter.aoc2018.day02

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("2018 Day 02 - Inventory Management System")
internal class InventoryManagementSystemKtTest {
    @Test
    fun boxCount() {
        val input = listOf("abcdef", "bababc", "abbcde", "abcccd", "aabcdd", "abcdee", "ababab")
        val actual = boxCount(input)
        Assertions.assertEquals(12, actual)
    }

    @Test
    fun findBoxes() {
        val input = listOf("abcde", "fghij", "klmno", "pqrst", "fguij", "axcye", "wvxyz")
        val actual = findBoxes(input)
        Assertions.assertEquals("fgij", actual)
    }
}