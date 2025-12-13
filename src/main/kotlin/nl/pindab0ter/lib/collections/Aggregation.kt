package nl.pindab0ter.lib.collections

import nl.pindab0ter.lib.pow

/**
 * Joins the digits in this list into a single [ULong] number.
 *
 * For example, `listOf(1, 2, 3).joinToULong()` returns `123uL`.
 *
 * @return The unsigned long integer formed by concatenating the digits in this list.
 */
fun List<Int>.joinToULong(): ULong = foldIndexed(0uL) { index, acc, x ->
    acc + x.toULong() * (10uL.pow(size - index - 1))
}

/**
 * @return The product of the result of [selector] for each element in the iterable.
 */
fun <T> Iterable<T>.productOf(selector: (T) -> Int): Int = fold(1) { acc, element -> acc * selector(element) }
