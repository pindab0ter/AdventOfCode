package nl.pindab0ter.lib

class DisjointSet<T>(universe: Iterable<T>) {
    private val parent = mutableMapOf<T, T>()
    private val trees = mutableMapOf<T, MutableSet<T>>()
    val components: Collection<Set<T>>
        get() = trees.values

    init {
        universe.forEach { item ->
            parent[item] = item
            trees[item] = mutableSetOf(item)
        }
    }

    fun find(element: T): T {
        if (parent[element] != element) {
            parent[element] = find(parent[element]!!)
        }
        return parent[element]!!
    }

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
