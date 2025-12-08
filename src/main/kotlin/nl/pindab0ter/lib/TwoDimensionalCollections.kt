package nl.pindab0ter.lib

import kotlin.math.max
import kotlin.math.min

fun Iterable<Coordinate>.contains(x: Long, y: Long): Boolean = any { it.x == x && it.y == y }

fun Iterable<Coordinate>.contains(x: Int, y: Int): Boolean = contains(x.toLong(), y.toLong())

/**
 * @return [Coordinate] (x, y) of the first element matching the given [predicate], or `null` if the collection does not
 * contain such an element.
 */
fun <T> Iterable<Iterable<T>>.coordinateOfFirst(predicate: (T) -> Boolean): Coordinate? {
    for ((y, row) in withIndex()) {
        for ((x, cell) in row.withIndex()) {
            if (predicate(cell)) return Coordinate(x.toLong(), y.toLong())
        }
    }

    return null
}

/**
 * @return [Coordinate] (x, y) of the first element in the collection that is in the given [collection], or `null` if the
 * collection does not contain such an element.
 */
fun <T> Iterable<Iterable<T>>.coordinateOfAny(collection: Iterable<T>): Coordinate? =
    coordinateOfFirst { it in collection }

/**
 * @return The first element matching the given predicate, or null if no such element was found.
 */
fun <T> Iterable<Iterable<T>>.find(predicate: (T) -> Boolean): T? = flatten().find(predicate)

/**
 * @return The element at the given [coordinate], or null if the coordinates are out of bounds.
 * @see Coordinate
 */
fun <T> List<List<T>>.getOrNull(coordinate: Coordinate): T? = this
    .getOrNull(coordinate.y.toInt())
    ?.getOrNull(coordinate.x.toInt())

operator fun <T> List<List<T>>.get(coordinate: Coordinate): T = this[coordinate.y.toInt()][coordinate.x.toInt()]

operator fun <T> MutableList<MutableList<T>>.set(coordinate: Coordinate, value: T) {
    this[coordinate.y.toInt()][coordinate.x.toInt()] = value
}

/**
 * @return The element at the given [x] and [y] coordinate, or null if the coordinates are out of bounds.
 * @see Coordinate
 */
fun <T> List<List<T>>.getOrNull(x: Int, y: Int): T? = getOrNull(y)?.getOrNull(x)

operator fun <T> List<List<T>>.get(x: Int, y: Int): T = get(y)[x]

/**
 * Return a new two-dimensional array, rotated 45º clockwise.
 *
 * Works for rectangular matrices.
 *
 * ```
 * Input:
 * 1 2 3
 * 4 5 6
 * 7 8 9
 *
 * Output:
 * 1
 * 4 2
 * 7 5 3
 * 8 6
 * 9
 *
 * Input:
 * 1 2 3 4
 * 5 6 7 8
 *
 * Output:
 * 1
 * 5 2
 * 6 3
 * 7 4
 * 8
 * ```
 *
 * [Credit to Greg Whittier](https://stackoverflow.com/a/33769730/3021748)
 */
fun <T> List<List<T>>.rotate45Degrees(): List<List<T>> {
    val height = first().count()
    val width = count()
    val diagonals = width + height - 1

    val rotatedList = MutableList(diagonals) { mutableListOf<T>() }

    (0 until diagonals).forEach { diagonal ->
        val columns = max(0, diagonal - width + 1)..min(diagonal, height - 1)

        columns.forEach { x ->
            val y = diagonal - x
            rotatedList[diagonal].add(this[y][x])
        }
    }

    return rotatedList
}

/**
 * Return a new two-dimensional array, rotated 45º anti-clockwise.
 *
 * Works for rectangular matrices.
 *
 * ```
 * Input:
 * 1 2 3
 * 4 5 6
 * 7 8 9
 *
 * Output:
 * 3
 * 6 2
 * 9 5 1
 * 8 4
 * 7
 *
 * Input:
 * 1 2 3 4
 * 5 6 7 8
 *
 * Output:
 * 4
 * 8 3
 * 7 2
 * 6 1
 * 5
 * ```
 */
fun <T> List<List<T>>.rotate45DegreesAntiClockwise(): List<List<T>> {
    val height = first().count()
    val width = count()
    val diagonals = width + height - 1

    val rotatedList = MutableList(diagonals) { mutableListOf<T>() }

    (0 until diagonals).forEach { diagonal ->
        val columns = height - 1 - min(diagonal, height - 1)..height - 1 - max(0, diagonal - width + 1)

        columns.reversed().forEach { x ->
            val y = diagonal - (height - 1 - x)
            rotatedList[diagonal].add(this[y][x])
        }
    }

    return rotatedList
}

/**
 * Transposes the collection. The resulting collection will have the same number of elements, but the rows and columns
 * will be swapped.
 *
 * ```
 * [[1, 2], [1, 2]]       -> [[1, 1], [2, 2]]
 * [[1, 1, 1], [2, 2, 2]] -> [[1, 2], [1, 2], [1, 2]]
 * ```
 */
fun <T> List<List<T>>.transpose(): List<List<T>> {
    val rows = this.toList()
    if (this.isEmpty()) return this

    val columnCount = rows.first().count()

    if (rows.any { row -> row.count() != columnCount }) {
        throw IllegalArgumentException("All rows must have the same size")
    }

    return this.first().indices.map { columnIndex ->
        this.indices.map { rowIndex ->
            this.elementAt(rowIndex).elementAt(columnIndex)
        }
    }
}
