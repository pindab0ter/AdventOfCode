package nl.pindab0ter.lib.collections

import nl.pindab0ter.lib.types.Coordinate

/**
 * A two-dimensional grid data structure that stores elements in a rectangular array.
 *
 * The grid uses a coordinate system where:
 * - The origin (0, 0) is at the top-left corner
 * - X coordinates increase to the right (columns)
 * - Y coordinates increase downward (rows)
 *
 * The grid provides efficient access to rows, columns, and neighboring cells, and implements
 * [Iterable] to allow iteration over all elements.
 *
 * Example:
 * ```
 * val grid = listOf(
 *     listOf(1, 2, 3),
 *     listOf(4, 5, 6),
 *     listOf(7, 8, 9)
 * ).toGrid()
 *
 * grid.width   // 3
 * grid.height  // 3
 * grid[Coordinate(1, 1)]  // 5 (center element)
 * grid.neighbours(1, 1)   // [1, 2, 3, 4, 6, 7, 8, 9] (all 8 neighbors)
 * ```
 *
 * @param T The type of elements stored in the grid.
 * @property rows The underlying row-based representation of the grid.
 * @property columns The column-based view of the grid.
 * @property width The number of columns in the grid.
 * @property height The number of rows in the grid.
 */
class Grid<T>(val rows: List<List<T>>) : Iterable<T> {
    /**
     * Creates a grid from vararg columns.
     *
     * @param rows The columns to create the grid from.
     */
    constructor(vararg rows: List<T>) : this(rows.toList())

    /**
     * A column-based view of the grid.
     *
     * Each element in this list represents one column (vertical slice) of the grid.
     */
    val columns: List<List<T>> = rows[0].indices.map { x -> rows.indices.map { y -> rows[x, y] } }

    /**
     * The number of columns (width) in the grid.
     */
    val width: Int = columns.size

    /**
     * The number of rows (height) in the grid.
     */
    val height: Int = rows.size

    init {
        require(rows.all { it.size == rows.first().size })
        require(columns.all { it.size == columns.first().size })
    }

    /**
     * Returns the row at the specified index.
     *
     * @param y The row index (0-based from the top).
     * @return A list containing all elements in the specified row.
     * @throws IndexOutOfBoundsException if [y] is out of bounds.
     */
    fun row(y: Int): List<T> = rows[y]

    /**
     * Returns the column at the specified index.
     *
     * @param x The column index (0-based from the left).
     * @return A list containing all elements in the specified column.
     * @throws IndexOutOfBoundsException if [x] is out of bounds.
     */
    fun column(x: Int): List<T> = columns[x]

    /**
     * Returns all neighboring cells of the cell at position ([x], [y]).
     *
     * Neighbors include all 8 adjacent cells (orthogonal and diagonal). Cells on the edges or corners
     * will have fewer neighbors. The cell itself is not included in the result.
     *
     * Example for a 3x3 grid:
     * ```
     * 1 2 3
     * 4 5 6    neighbours(1, 1) returns [1, 2, 3, 4, 6, 7, 8, 9]
     * 7 8 9
     * ```
     *
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @return A list of neighboring elements, excluding cells outside grid bounds.
     */
    fun neighbours(x: Int, y: Int): List<T> {
        val result = mutableListOf<T>()
        for (currentY in y - 1..y + 1) {
            for (currentX in x - 1..x + 1) {
                if (currentX == x && currentY == y) continue
                rows.getOrNull(currentX, currentY)?.let { result.add(it) }
            }
        }
        return result
    }

    /**
     * Returns all neighboring cells of the cell at the given [coordinate].
     *
     * @param coordinate The coordinate of the cell.
     * @return A list of neighboring elements.
     *
     * @see neighbours
     */
    fun neighbours(coordinate: Coordinate): List<T> = neighbours(coordinate.x.toInt(), coordinate.y.toInt())

    /**
     * Finds the coordinate of the first occurrence of the given [element] in the grid.
     *
     * The grid is searched row by row, from top to bottom and left to right within each row.
     *
     * @param element The element to search for.
     * @return The coordinate of the first occurrence of [element].
     * @throws NoSuchElementException if the element is not found in the grid.
     *
     * @see Coordinate
     */
    fun coordinateOf(element: T): Coordinate = rows
        .asSequence()
        .withIndex()
        .firstNotNullOf { (y, row) ->
            row
                .indexOf(element)
                .takeIf { it >= 0 }
                ?.let { x -> Coordinate(x, y) }
        }

    /**
     * Returns a new grid containing the results of applying the given [transform] function to each element and its coordinate.
     *
     * The [transform] function is called with the coordinate of each element and the element itself. Elements are processed
     * column by column, from left to right, and within each column from top to bottom.
     *
     * Example:
     * ```
     * val grid = listOf(
     *     listOf(1, 2),
     *     listOf(3, 4)
     * ).toGrid()
     *
     * val result = grid.mapIndexed { coordinate, value ->
     *     "${coordinate.x},${coordinate.y}:$value"
     * }
     * // Result grid:
     * // "0,0:1" "0,1:2"
     * // "1,0:3" "1,1:4"
     * ```
     *
     * @param transform A function that takes a coordinate and the element at that position and returns the transformed value.
     * @return A new grid with the results of applying [transform] to each element.
     *
     * @see Grid.map
     * @see Iterable.mapIndexed
     */
    fun <R> mapIndexed(
        transform: (coordinate: Coordinate, value: T) -> R,
    ): Grid<R> {
        val destination = MutableList(height) { mutableListOf<R>() }
        for (x in 0 until width) {
            for (y in 0 until height) {
                val coordinate = Coordinate(x, y)
                destination[y].add(transform(coordinate, rows[coordinate]))
            }
        }
        return destination.toGrid()
    }

    /**
     * Returns the element at the given [coordinate].
     *
     * @param coordinate The coordinate of the element to retrieve.
     * @return The element at the specified coordinate.
     * @throws IndexOutOfBoundsException if the coordinate is out of bounds.
     *
     * @see getOrNull
     * @see Coordinate
     */
    operator fun get(coordinate: Coordinate): T = rows[coordinate.x.toInt(), coordinate.y.toInt()]

    /**
     * Returns the element at the given [coordinate], or `null` if the coordinate is out of bounds.
     *
     * @param coordinate The coordinate of the element to retrieve.
     * @return The element at the specified coordinate, or `null` if out of bounds.
     *
     * @see get
     * @see Coordinate
     */
    fun getOrNull(coordinate: Coordinate): T? = rows.getOrNull(coordinate.x.toInt(), coordinate.y.toInt())

    /**
     * Returns a string representation of the grid with rows separated by newlines and elements separated by spaces.
     *
     * Example:
     * ```
     * val grid = listOf(
     *     listOf(1, 2),
     *     listOf(3, 4)
     * ).toGrid()
     * println(grid)
     * // Output:
     * // 1 2
     * // 3 4
     * ```
     *
     * @return A string representation of the grid.
     */
    override fun toString(): String = this@Grid.rows.joinToString("\n") { row -> row.joinToString(" ") }

    /**
     * Returns an iterator over all elements in the grid.
     *
     * Elements are iterated row by row, from top to bottom and left to right within each row.
     *
     * @return An iterator over all elements in the grid.
     */
    override fun iterator(): Iterator<T> = this@Grid.rows.flatten().iterator()
}

/**
 * Converts this list of lists into a [Grid].
 *
 * The outer list represents rows, and each inner list represents a row of elements.
 * All rows must have the same length.
 *
 * Example:
 * ```
 * val grid = listOf(
 *     listOf(1, 2, 3),
 *     listOf(4, 5, 6)
 * ).toGrid()
 * ```
 *
 * @return A grid containing the elements from this list.
 * @throws IllegalArgumentException if rows have different lengths.
 */
fun <T> List<List<T>>.toGrid(): Grid<T> = Grid(this)

/**
 * Converts this string into a [Grid] of characters.
 *
 * The string is split by newlines, and each line becomes a row in the grid.
 * Each character in a line becomes an element in that row.
 *
 * Example:
 * ```
 * val grid = """
 *     ABC
 *     DEF
 *     GHI
 * """.trimIndent().toGrid()
 * // Creates a 3x3 grid of characters
 * ```
 *
 * @return A grid of characters.
 * @throws IllegalArgumentException if lines have different lengths.
 */
fun String.toGrid(): Grid<Char> = lines().map(String::toList).toGrid()
