package nl.pindab0ter.lib

import kotlin.test.assertEquals

/**
 * Asserts that all [pairs]s are equal.
 *
 * @param pairs The [pairs]s to compare. The first element is the actual value, the second element is the expected value.
 */
fun <T> assertAllEquals(vararg pairs: Pair<T, T>) = pairs
    .forEach { (expected, actual) ->
        assertEquals(expected, actual)
    }
