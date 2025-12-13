package nl.pindab0ter.lib.collections

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

fun CharSequence.grouped(): Collection<List<Char>> = groupBy { it }.values

/** Transforms elements asynchronously, suspending until all transformations complete. */
fun <T, R> Iterable<T>.mapAsync(transform: (T) -> R): List<R> = runBlocking {
    map {
        async(Dispatchers.Default) {
            transform(it)
        }
    }.awaitAll()
}

fun <T> Iterable<T>.replace(index: Int, transform: (T) -> T): List<T> = mapIndexed { i, e ->
    if (i == index) transform(e) else e
}

fun <T> Iterable<T>.replace(index: Int, element: T): Iterable<T> = mapIndexed { i, e ->
    if (i == index) element else e
}

fun <T> Iterable<T>.replaceLast(element: T): Iterable<T> = replace(count() - 1, element)
fun <T> Iterable<T>.replaceLast(transform: (T) -> T): List<T> = replace(count() - 1, transform)
