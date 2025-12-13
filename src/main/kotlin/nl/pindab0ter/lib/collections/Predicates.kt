package nl.pindab0ter.lib.collections

fun <T> Iterable<T>.allElementsEqual(): Boolean = toSet().size == 1
fun <T> Iterable<T>.containsAll(vararg elements: T): Boolean = elements.all { it in this }
