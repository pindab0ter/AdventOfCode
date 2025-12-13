package nl.pindab0ter.lib.collections

import nl.pindab0ter.lib.types.Coordinate
import kotlin.math.max
import kotlin.math.min

/**
 * Returns the [Coordinate] of the first element that is contained in the given [collection], or `null` if no such element was found.
 *
 * The search is performed row by row, from top to bottom and left to right within each row.
 *
 * @param collection The collection of elements to search for.
 * @return The coordinate (x, y) of the first matching element, or `null` if no element from [collection] is found.
 *
 * @see coordinateOfFirst
 */
fun <T> Iterable<Iterable<T>>.coordinateOfAny(collection: Iterable<T>): Coordinate? =
    coordinateOfFirst { it in collection }

/**
 * Returns the [Coordinate] of the first element matching the given [predicate], or `null` if no such element was found.
 *
 * The search is performed row by row, from top to bottom and left to right within each row.
 *
 * @param predicate The function to test elements for a match.
 * @return The coordinate (x, y) of the first matching element, or `null` if the collection does not contain such an element.
 *
 * @see Iterable.find
 */
fun <T> Iterable<Iterable<T>>.coordinateOfFirst(predicate: (T) -> Boolean): Coordinate? {
    for ((y, row) in withIndex()) {
        for ((x, cell) in row.withIndex()) {
            if (predicate(cell)) return Coordinate(x.toLong(), y.toLong())
        }
    }

    return null
}

/** Flattens and searches the 2D collection. */
fun <T> Iterable<Iterable<T>>.find(predicate: (T) -> Boolean): T? = flatten().find(predicate)

operator fun <T> List<List<T>>.get(coordinate: Coordinate): T = this[coordinate.y.toInt()][coordinate.x.toInt()]
operator fun <T> List<List<T>>.get(x: Int, y: Int): T = get(y)[x]
fun <T> List<List<T>>.getOrNull(coordinate: Coordinate): T? = this
    .getOrNull(coordinate.y.toInt())
    ?.getOrNull(coordinate.x.toInt())

/**
 * Returns the element at the given x and y coordinates, or `null` if the coordinates are out of bounds.
 *
 * @param x The x-coordinate (column index).
 * @param y The y-coordinate (row index).
 * @return The element at the specified coordinates, or `null` if the coordinates are out of bounds.
 *
 * @see getOrNull
 */
fun <T> List<List<T>>.getOrNull(x: Int, y: Int): T? = getOrNull(y)?.getOrNull(x)

/**
 * Rotates 45 degrees clockwise.
 * ```
 * 1 2 3           1
 * 4 5 6    →     4 2
 * 7 8 9           7 5 3
 *                 8 6
 *                 9
 * ```
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
 * Rotates 45 degrees counter-clockwise.
 * ```
 * 1 2 3           3
 * 4 5 6    →     6 2
 * 7 8 9           9 5 1
 *                 8 4
 *                 7
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

operator fun <T> MutableList<MutableList<T>>.set(coordinate: Coordinate, value: T) {
    this[coordinate.y.toInt()][coordinate.x.toInt()] = value
}

/**
 * Transposes rows and columns.
 * ```
 * [[1, 2], [1, 2]]       -> [[1, 1], [2, 2]]
 * [[1, 2], [1, 2]] -> [[1, 1], [2, 2]]
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
