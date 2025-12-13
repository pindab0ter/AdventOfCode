package nl.pindab0ter.lib.collections

import nl.pindab0ter.lib.assertAllEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Predicates")
class PredicatesKtTest {

    @Nested
    @DisplayName("allElementsEqual")
    inner class AllElementsEqual {
        @Test
        fun `Lists of integers`() = assertAllEquals(
            true to listOf(1, 1).allElementsEqual(),
            false to listOf(0, 1).allElementsEqual(),
        )

        @Test
        fun `Lists of lists of integers`() = assertAllEquals(
            true to listOf(listOf(1), listOf(1)).allElementsEqual(),
            false to listOf(listOf(0), listOf(1)).allElementsEqual(),
        )

        @Test
        fun `Lists of pairs of integers`() = assertAllEquals(
            true to listOf(0 to 1, 0 to 1).allElementsEqual(),
            false to listOf(0 to 0, 0 to 1).allElementsEqual(),
        )
    }
}
