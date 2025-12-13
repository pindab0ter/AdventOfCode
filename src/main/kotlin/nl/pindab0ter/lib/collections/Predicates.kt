package nl.pindab0ter.lib.collections

import nl.pindab0ter.lib.types.Coordinate

fun <T> Iterable<T>.allElementsEqual(): Boolean = toSet().size == 1
fun Iterable<Coordinate>.contains(x: Int, y: Int): Boolean = contains(x.toLong(), y.toLong())
fun Iterable<Coordinate>.contains(x: Long, y: Long): Boolean = contains(Coordinate(x, y))
fun <T> Iterable<T>.containsAll(vararg elements: T): Boolean = elements.all { it in this }
