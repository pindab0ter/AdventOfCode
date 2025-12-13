package nl.pindab0ter.lib

class Grid<T>(val rows: List<List<T>>) : Iterable<T> {
    constructor(vararg columns: List<T>) : this(columns.toList())

    val columns: List<List<T>> = rows[0].indices.map { x -> rows.indices.map { y -> rows[x, y] } }
    val width: Int = columns.size
    val height: Int = rows.size

    init {
        require(rows.all { it.size == rows.first().size })
        require(columns.all { it.size == columns.first().size })
    }

    fun row(y: Int): List<T> = rows[y]
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

    fun coordinateOf(element: T): Coordinate = rows
        .asSequence()
        .withIndex()
        .firstNotNullOf { (y, row) ->
            row
                .indexOf(element)
                .takeIf { it >= 0 }
                ?.let { x -> Coordinate(x, y) }
        }

    operator fun get(coordinate: Coordinate): T = rows[coordinate.x.toInt(), coordinate.y.toInt()]
    fun getOrNull(coordinate: Coordinate): T? = rows.getOrNull(coordinate.x.toInt(), coordinate.y.toInt())

    override fun toString(): String = this@Grid.rows.joinToString("\n") { row -> row.joinToString(" ") }
    override fun iterator(): Iterator<T> = this@Grid.rows.flatten().iterator()
}

fun <T> List<List<T>>.toGrid(): Grid<T> = Grid(this)
fun String.toGrid(): Grid<Char> = lines().map(String::toList).toGrid()

/**
 * Applies the given [transform] function to each cell in the grid and its coordinates in the original grid
 * and appends the results to a new [Grid].
 * @param [transform] function that takes the coordinates of an element and the element itself
 * and returns the result of the transform applied to the element.
 */
inline fun <T, R> Grid<T>.mapIndexed(
    transform: (coordinate: Coordinate, value: T) -> R,
): Grid<R> {
    val destination = MutableList(height) { mutableListOf<R>() }
    for (x in 0 until width) {
        for (y in 0 until height) {
            val coordinate = Coordinate(x, y)
            destination[y]
                .add(transform(
                    coordinate, rows
                        .get(y)
                        .get(x)
                ))
        }
    }
    return destination.toGrid()
}
