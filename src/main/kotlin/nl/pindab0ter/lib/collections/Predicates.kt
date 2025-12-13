package nl.pindab0ter.lib.collections

import nl.pindab0ter.lib.types.Point

fun <T> Iterable<T>.allElementsEqual(): Boolean = toSet().size == 1
fun Iterable<Point>.contains(x: Int, y: Int): Boolean = contains(x.toLong(), y.toLong())
fun Iterable<Point>.contains(x: Long, y: Long): Boolean = contains(Point(x, y))
fun <T> Iterable<T>.containsAll(vararg elements: T): Boolean = elements.all { it in this }
