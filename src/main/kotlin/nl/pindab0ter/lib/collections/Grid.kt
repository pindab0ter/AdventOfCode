@file:JvmName("MatrixKt")

package nl.pindab0ter.lib.collections

import nl.pindab0ter.lib.types.Point

/** A two-dimensional grid data structure. Origin (0, 0) is top-left, x increases right, y increases down. */
class Grid<T>(val width: Int, val height: Int, private val data: List<T>) : Collection<T> {
    constructor(rows: List<List<T>>) : this(rows.first().size, rows.size, rows.flatten())
    constructor(vararg rows: List<T>) : this(rows.toList())

    override val size: Int get() = data.size

    init {
        require(data.size == width * height)
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun index(x: Int, y: Int) = y * width + x

    @Suppress("NOTHING_TO_INLINE")
    private inline fun pointOf(index: Int) = Point(index % width, index / width)

    fun rows(): List<RowView> = (0 until height).map { RowView(it) }
    fun columns(): List<ColumnView> = (0 until width).map { ColumnView(it) }

    fun row(index: Int): RowView = RowView(index)
    fun column(index: Int): ColumnView = ColumnView(index)

    override fun isEmpty(): Boolean = height == 0
    override fun contains(element: T): Boolean = data.contains(element)
    override fun containsAll(elements: Collection<T>): Boolean = data.containsAll(elements)

    override fun iterator(): Iterator<T> = data.iterator()

    operator fun get(x: Int, y: Int): T {
        if (x !in 0..<width || y !in 0..<height) throw IndexOutOfBoundsException()
        return data[index(x, y)]
    }

    operator fun get(point: Point): T = this[point.x.toInt(), point.y.toInt()]

    fun getOrNull(x: Int, y: Int): T? =
        if (x !in 0..<width || y !in 0..<height) null
        else data.getOrNull(index(x, y))

    fun getOrNull(point: Point): T? = getOrNull(point.x.toInt(), point.y.toInt())

    /**
     * Returns all 8 neighbouring cells (orthogonal and diagonal).
     * ```
     * 1 2 3
     * 4 5 6    neighbours(1, 1) → [1, 2, 3, 4, 6, 7, 8, 9]
     * 7 8 9
     * ```
     */
    fun neighbours(x: Int, y: Int): List<T> {
        val result = mutableListOf<T>()
        for (currentY in y - 1..y + 1) {
            for (currentX in x - 1..x + 1) {
                if (currentX == x && currentY == y) continue
                getOrNull(currentX, currentY)?.let { result.add(it) }
            }
        }
        return result
    }

    fun neighbours(point: Point): List<T> = neighbours(point.x.toInt(), point.y.toInt())

    /** Returns the point of the first occurrence of the element. */
    fun pointOf(element: T): Point = pointOf(data.indexOf(element))

    /** Transforms each element along with its point. */
    fun <R> mapIndexed(
        transform: (point: Point, value: T) -> R,
    ): Grid<R> = Grid(width, height, data.mapIndexed { index, element ->
        transform(pointOf(index), element)
    })

    override fun toString(): String {
        val longest = maxOf { it.toString().length }
        var index = 0
        return joinToString(separator = " ") {
            val string = it.toString().padStart(longest)
            if (++index % width == 1 && index > 1) "\n$string" else string
        }
    }

    /** Base class for row and column views. These are views over the grid data, not copies. */
    abstract inner class ListView : List<T> {
        override fun indexOf(element: T): Int = (0 until size).indexOfFirst { get(it) == element }
        override fun lastIndexOf(element: T): Int = (0 until size).indexOfLast { get(it) == element }
        override fun subList(fromIndex: Int, toIndex: Int): List<T> = (fromIndex until toIndex).map { get(it) }
        override fun toString(): String = joinToString(prefix = "[", postfix = "]")

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is List<*>) return false
            if (size != other.size) return false
            return (0 until size).all { get(it) == other[it] }
        }

        override fun hashCode(): Int {
            var result = 1
            for (i in 0 until size) {
                result = 31 * result + (get(i)?.hashCode() ?: 0)
            }
            return result
        }
    }

    /**
     * A view of a single row in the grid (not a copy).
     */
    inner class RowView(val y: Int) : ListView() {
        override val size: Int get() = width

        override fun get(index: Int): T = data[index(index, y)]
        override fun isEmpty(): Boolean = width == 0

        override fun contains(element: T): Boolean = data
            .slice(index(0, y) until index(width, y))
            .contains(element)

        override fun containsAll(elements: Collection<T>): Boolean = data
            .slice(index(0, y) until index(width, y))
            .containsAll(elements)

        override fun iterator(): Iterator<T> = this.RowViewIterator(0)
        override fun listIterator(): ListIterator<T> = this.RowViewIterator(0)
        override fun listIterator(index: Int): ListIterator<T> = this.RowViewIterator(index)

        private inner class RowViewIterator(private var x: Int) : ListIterator<T> {
            override fun hasNext(): Boolean = x < width
            override fun hasPrevious(): Boolean = x > 0
            override fun previousIndex(): Int = x - 1

            override fun nextIndex(): Int = x + 1

            override fun next(): T = if (x >= width) throw NoSuchElementException() else get(x++, y)
            override fun previous(): T = if (x < 0) throw NoSuchElementException() else get(x--, y)
        }
    }

    /** A view of a single column in the grid (not a copy). */
    inner class ColumnView(val x: Int) : ListView() {
        override val size: Int get() = height

        override fun get(index: Int): T = data[index(x, index)]
        override fun isEmpty(): Boolean = height == 0

        /** Returns the point of the first element matching the [predicate], starting from the given [index][fromIndex]. */
        fun pointOfFirst(fromIndex: Int = 0, predicate: (T) -> Boolean): Point? {
            var index = 0
            val y = indexOfFirst { element ->
                val found = if (index < fromIndex) false else predicate(element)

                index++
                found
            }
            if (y == -1) return null
            return Point(x, y)
        }

        override fun contains(element: T): Boolean = data
            .slice(index(x, 0) until index(x, height))
            .contains(element)

        override fun containsAll(elements: Collection<T>): Boolean = data
            .slice(index(x, 0) until index(x, height))
            .containsAll(elements)

        override fun iterator(): Iterator<T> = this.ColumnViewIterator(0)
        override fun listIterator(): ListIterator<T> = this.ColumnViewIterator(0)
        override fun listIterator(index: Int): ListIterator<T> = this.ColumnViewIterator(index)

        private inner class ColumnViewIterator(private var y: Int) : ListIterator<T> {
            override fun hasNext(): Boolean = y < height
            override fun hasPrevious(): Boolean = y > 0
            override fun previousIndex(): Int = y - 1

            override fun nextIndex(): Int = y + 1

            override fun next(): T = if (y >= height) throw NoSuchElementException() else get(x, y++)
            override fun previous(): T = if (y < 0) throw NoSuchElementException() else get(x, y--)
        }
    }
}

/** Converts this list of lists (rows) into a grid. */
fun <T> List<List<T>>.toGrid(): Grid<T> = Grid(this)

/** Converts this string into a grid of characters (split by newlines). */
fun String.toGrid(): Grid<Char> = lines().map(String::toList).toGrid()
