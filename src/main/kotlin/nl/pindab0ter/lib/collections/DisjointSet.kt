package nl.pindab0ter.lib.collections

/**
 * A disjoint-set data structure (also called union-find) that tracks a partition of a set into disjoint subsets.
 *
 * This data structure supports two operations:
 * - **Find**: Determine which subset a particular element is in
 * - **Union**: Join two subsets into a single subset
 *
 * The implementation uses path compression in [find] and union by size in [union] for efficient operations.
 * Both operations run in nearly O(1) amortized time (inverse Ackermann function).
 *
 * Common use cases:
 * - Finding connected components in a graph
 * - Kruskal's minimum spanning tree algorithm
 * - Detecting cycles in undirected graphs
 * - Percolation problems
 *
 * Example:
 * ```
 * val set = DisjointSet(listOf(1, 2, 3, 4, 5))
 * set.union(1, 2)  // Connect 1 and 2
 * set.union(3, 4)  // Connect 3 and 4
 * set.union(2, 3)  // Connect the {1,2} component with {3,4}
 *
 * set.find(1) == set.find(4)  // true: 1 and 4 are in the same component
 * set.find(1) == set.find(5)  // false: 5 is in its own component
 * set.components.size         // 2: {1,2,3,4} and {5}
 * ```
 *
 * @param T The type of elements in the disjoint set.
 * @property components The current connected components as a collection of sets.
 */
class DisjointSet<T>(universe: Iterable<T>) {
    private val parent = mutableMapOf<T, T>()
    private val trees = mutableMapOf<T, MutableSet<T>>()

    /**
     * The current connected components in the disjoint set.
     *
     * Each component is represented as a set of elements that are connected to each other.
     */
    val components: Collection<Set<T>>
        get() = trees.values

    init {
        universe.forEach { item ->
            parent[item] = item
            trees[item] = mutableSetOf(item)
        }
    }

    /**
     * Adds a new element to the disjoint set as a singleton component.
     *
     * The element starts in its own component, not connected to any other element.
     *
     * @param element The element to add to the disjoint set.
     */
    fun add(element: T) {
        parent[element] = element
    }

    /**
     * Finds the representative (root) element of the component containing the given [element].
     *
     * Uses path compression: all nodes along the path to the root are updated to point directly to the root,
     * optimizing future find operations.
     *
     * Two elements are in the same component if and only if their find results are equal.
     *
     * @param element The element to find the representative for.
     * @return The representative element of the component containing [element].
     */
    fun find(element: T): T {
        if (parent[element] != element) {
            parent[element] = find(parent[element]!!)
        }
        return parent[element]!!
    }

    /**
     * Unites the components containing elements [a] and [b] into a single component.
     *
     * If [a] and [b] are already in the same component, this operation has no effect.
     * Uses union by size: the smaller tree is attached to the root of the larger tree to keep trees shallow.
     *
     * Example:
     * ```
     * val set = DisjointSet(listOf(1, 2, 3, 4))
     * set.union(1, 2)  // Connect 1 and 2
     * set.union(3, 4)  // Connect 3 and 4
     * set.find(1) == set.find(2)  // true
     * set.find(1) == set.find(3)  // false
     * set.union(2, 3)  // Connect the two components
     * set.find(1) == set.find(4)  // true: all are now connected
     * ```
     *
     * @param a The first element.
     * @param b The second element.
     */
    fun union(a: T, b: T) {
        val rootOfA = find(a)
        val rootOfB = find(b)

        if (rootOfA != rootOfB) {
            val treeOfA = trees[rootOfA]!!
            val treeOfB = trees[rootOfB]!!

            if (treeOfA.size < treeOfB.size) {
                parent[rootOfA] = rootOfB
                treeOfB.addAll(treeOfA)
                trees.remove(rootOfA)
            } else {
                parent[rootOfB] = rootOfA
                treeOfA.addAll(treeOfB)
                trees.remove(rootOfB)
            }
        }
    }

    override fun toString(): String {
        return "DisjointSet(trees=$trees)"
    }
}
