package nl.pindab0ter.lib

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

/**
 * @return True if all elements in the iterable are equal, false otherwise.
 */
fun <T> Iterable<T>.allElementsEqual(): Boolean = toSet().size == 1

fun <T> Iterable<T>.containsAll(vararg elements: T): Boolean = elements.all { it in this }

/**
 * @return A collection of lists of characters grouped by their value.
 */
fun CharSequence.grouped(): Collection<List<Char>> = groupBy { it }.values

/**
 * @return The first element in the iterable.
 */
fun <T> Iterable<T>.head(): T = first()

/**
 * @return The second element in the iterable.
 */
fun <T> Iterable<T>.second(): T = elementAt(1)

/**
 * @return A lazy sequence of the results of applying the given [transform] function to the receiver, and then applying
 * the function to the result, and so on.
 */
fun <T> T.iterate(transform: (T) -> T): Sequence<T> = sequence {
    var acc = this@iterate
    while (true) {
        yield(acc)
        acc = transform(acc)
    }
}

/**
 * Transforms the elements of the iterable asynchronously using the provided [transform] function. The function suspends
 * until all transformations are complete.
 *
 * @param transform The function to apply to each element. This function is invoked asynchronously on a separate coroutine.
 * @return A new iterable with the transformed elements.
 */
fun <T, R> Iterable<T>.mapAsync(transform: (T) -> R): List<R> = runBlocking {
    map {
        async(Dispatchers.Default) {
            transform(it)
        }
    }.awaitAll()
}

/**
 * Returns the product of the result of [selector] for each element in the iterable.
 *
 * @param selector The function to apply to each element.
 * @return The product of the result of [selector] for each element in the iterable.
 */
fun <T> Iterable<T>.productOf(selector: (T) -> Int): Int = fold(1) { acc, element -> acc * selector(element) }

fun <T> Iterable<T>.replace(index: Int, transform: (T) -> T): List<T> =
    mapIndexed { i, e -> if (i == index) transform(e) else e }

fun <T> Iterable<T>.replaceLast(transform: (T) -> T): List<T> = replace(count() - 1, transform)

fun <T> Iterable<T>.replace(index: Int, element: T): Iterable<T> = mapIndexed { i, e -> if (i == index) element else e }

fun <T> Iterable<T>.replaceLast(index: Int, element: T): Iterable<T> = replace(count() - 1, element)

/**
 * @return A new iterable with all but the last element.
 */
fun <T> Iterable<T>.init(): Iterable<T> = take(count() - 1)

/**
 * @return A new iterable with all elements after the first.
 */
fun <T> Iterable<T>.tail(): Iterable<T> = drop(1)

/**
 * @return A new iterable without the element at [index].
 */
fun <T> Iterable<T>.without(index: Int) = take(index) + drop(index + 1)

fun List<Int>.joinToULong(): ULong = foldIndexed(0uL) { index, acc, x ->
    acc + x.toULong() * (10uL.pow(size - index - 1))
}

/**
 * Returns all unique pairs of elements from this set.
 *
 * Each element is paired exactly once with every other element. The order within pairs is determined by the iteration
 * order of the set.
 *
 * For example, `setOf(1, 2, 3).combinations()` returns `setOf(1 to 2, 1 to 3, 2 to 3)`.
 *
 * @return A set of all unique pairs from this set.
 */
fun <T> Set<T>.combinations(): Set<Pair<T, T>> = withIndex()
    .flatMap { (i, a) ->
        drop(i + 1).map { b -> a to b }
    }
    .toSet()
