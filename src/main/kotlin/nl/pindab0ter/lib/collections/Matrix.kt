package nl.pindab0ter.lib.collections

import nl.pindab0ter.lib.types.Point
import kotlin.math.max
import kotlin.math.min

/** Flattens and searches the 2D collection. */
fun <T> Iterable<Iterable<T>>.find(predicate: (T) -> Boolean): T? = flatten().find(predicate)
operator fun <T> List<List<T>>.get(point: Point): T = this[point.y.toInt()][point.x.toInt()]
operator fun <T> List<List<T>>.get(x: Int, y: Int): T = get(y)[x]
fun <T> List<List<T>>.getOrNull(point: Point): T? = this
    .getOrNull(point.y.toInt())
    ?.getOrNull(point.x.toInt())

fun <T> List<List<T>>.getOrNull(x: Int, y: Int): T? = getOrNull(y)?.getOrNull(x)

fun <T> Iterable<Iterable<T>>.pointOfFirst(predicate: (T) -> Boolean): Point? {
    for ((y, row) in withIndex()) {
        for ((x, cell) in row.withIndex()) {
            if (predicate(cell)) return Point(x.toLong(), y.toLong())
        }
    }

    return null
}

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

operator fun <T> MutableList<MutableList<T>>.set(point: Point, value: T) {
    this[point.y.toInt()][point.x.toInt()] = value
}

/**
 * Transposes rows and columns.
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
