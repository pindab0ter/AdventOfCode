package nl.pindab0ter.lib.collections

/**
 * @return The first element in the iterable.
 */
fun <T> Iterable<T>.head(): T = first()

/**
 * @return A new iterable with all but the last element.
 */
fun <T> Iterable<T>.init(): Iterable<T> = take(count() - 1)

/**
* @return The second element in the iterable.
 */
fun <T> Iterable<T>.second(): T = elementAt(1)

/**
 * @return A new iterable with all elements after the first.
 */
fun <T> Iterable<T>.tail(): Iterable<T> = drop(1)
