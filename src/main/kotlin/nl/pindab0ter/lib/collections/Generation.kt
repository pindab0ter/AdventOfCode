package nl.pindab0ter.lib.collections

/**
 * Returns all unique pairs of elements from this iterable.
 *
 * Each element is paired exactly once with every other element. The order within pairs is determined by the iteration
 * order of the iterable.
 *
 * For example, `setOf(1, 2, 3).combinations()` returns `listOf(1 to 2, 1 to 3, 2 to 3)`.
 *
 * @return A set of all unique pairs from this set.
 */
fun <T> List<T>.combinations(): Sequence<Pair<T, T>> {
    val remaining = toMutableList().drop(1)
    var i = 0
    var j = 0

    fun next(): Pair<T,T>? {
        val a = getOrNull(i) ?: return null
        val b = remaining.getOrNull(j) ?: return null

        j++

        if (j >= remaining.size) {
            i++
            remaining.drop(1)
            j = i
        }

        return (a to b)
    }

    return generateSequence(::next)
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
 * @return A new iterable without the element at [index].
 */
fun <T> Iterable<T>.without(index: Int) = take(index) + drop(index + 1)
