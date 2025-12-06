package nl.pindab0ter.common

class Grid<T>(val rows: List<List<T>>) : Iterable<T> {
    constructor(vararg columns: List<T>) : this(columns.toList())

    val columns: List<List<T>> = rows[0].indices.map { x -> rows.indices.map { y -> rows[y, x] } }
    val width: Int = this@Grid.rows.size
    val height: Int = columns.size

    fun row(y: Int): List<T> = this@Grid.rows[y]
    fun column(x: Int): List<T> = columns[x]

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

    fun neighbours(coordinate: Coordinate): List<T> = neighbours(coordinate.x.toInt(), coordinate.y.toInt())

    init {
        require(this@Grid.rows.all { it.size == this@Grid.rows.first().size })
        require(columns.all { it.size == columns.first().size })
    }

    override fun toString(): String = this@Grid.rows.joinToString("\n") { row -> row.joinToString(" ") }
    override fun iterator(): Iterator<T> = this@Grid.rows.flatten().iterator()
}

fun <T> List<List<T>>.toGrid(): Grid<T> = Grid(this)

/**
 * Applies the given [transform] function to each cell in the grid and its coordinates in the original grid
 * and appends the results to a new [Grid].
 * @param [transform] function that takes the coordinates of an element and the element itself
 * and returns the result of the transform applied to the element.
 */
inline fun <T, R> Grid<T>.mapIndexed(
    transform: (x: Int, y: Int, value: T) -> R,
): Grid<R> {
    val destination = MutableList(width) { mutableListOf<R>() }
    for (x in 0 until width) {
        for (y in 0 until height) {
            destination[y].add(transform(x, y, rows[y][x]))
        }
    }
    return destination.toGrid()
}
