package nl.pindab0ter.common

/**
 * @return Coordinates (x, y) of the first element matching the given predicate, or null if the collection does not
 * contain such element.
 */
fun <T> Iterable<Iterable<T>>.coordinatesOfFirst(predicate: (T) -> Boolean): Coordinate? {
    for ((y, row) in withIndex()) {
        for ((x, cell) in row.withIndex()) {
            if (predicate(cell)) return Coordinate(x.toLong(), y.toLong())
        }
    }

    return null
}

/**
 * @return The first element matching the given predicate, or null if no such element was found.
 */
fun <T> Iterable<Iterable<T>>.find(predicate: (T) -> Boolean): T? = flatten().find(predicate)

/**
 * @return The element at the given [coordinate], or null if the coordinates are out of bounds.
 * @see Coordinate
 */
operator fun <T> List<List<T>>.get(coordinate: Coordinate): T? = this
    .getOrNull(coordinate.y.toInt())
    ?.getOrNull(coordinate.x.toInt())

/**
 * @return The element at the given [x] and [y] coordinate, or null if the coordinates are out of bounds.
 * @see Coordinate
 */
operator fun <T> List<List<T>>.get(x: Int, y: Int): T? = getOrNull(y)?.getOrNull(x)

/**
 * @return The element at the given [x] and [y] coordinate, or null if the coordinates are out of bounds.
 * @see Coordinate
 */
operator fun <T> List<List<T>>.get(x: Long, y: Long): T? = this[x.toInt(), y.toInt()]