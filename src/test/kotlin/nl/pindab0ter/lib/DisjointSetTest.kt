package nl.pindab0ter.lib

import nl.pindab0ter.lib.collections.DisjointSet
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

@DisplayName("DisjointSet")
class DisjointSetTest {

    @Nested
    @DisplayName("Initialization")
    inner class Initialization {

        @Test
        fun `Creates singleton sets for each element in universe`() {
            val disjointSet = DisjointSet(listOf(1, 2, 3, 4, 5))

            assertEquals(5, disjointSet.components.size)
            assertTrue(disjointSet.components.all { it.size == 1 })
        }

        @Test
        fun `Handles empty universe`() {
            val disjointSet = DisjointSet(emptyList<Int>())

            assertEquals(0, disjointSet.components.size)
        }
    }

    @Nested
    @DisplayName("Find")
    inner class Find {

        @Test
        fun `Returns element itself when no unions have been performed`() {
            val disjointSet = DisjointSet(listOf(1, 2, 3))

            assertEquals(1, disjointSet.find(1))
            assertEquals(2, disjointSet.find(2))
            assertEquals(3, disjointSet.find(3))
        }

        @Test
        fun `Returns same root for elements in same set`() {
            val disjointSet = DisjointSet(listOf(1, 2, 3))

            disjointSet.union(1, 2)

            val root1 = disjointSet.find(1)
            val root2 = disjointSet.find(2)

            assertEquals(root1, root2)
        }

        @Test
        fun `Returns different roots for elements in different sets`() {
            val disjointSet = DisjointSet(listOf(1, 2, 3))

            disjointSet.union(1, 2)

            val root1 = disjointSet.find(1)
            val root3 = disjointSet.find(3)

            assertNotEquals(root1, root3)
        }

        @Test
        fun `Path compression flattens tree structure`() {
            val disjointSet = DisjointSet(listOf(1, 2, 3, 4))

            // Create a chain: 1 -> 2 -> 3 -> 4
            disjointSet.union(1, 2)
            disjointSet.union(2, 3)
            disjointSet.union(3, 4)

            // After find, all elements should point directly to the root
            val root = disjointSet.find(1)

            assertEquals(root, disjointSet.find(2))
            assertEquals(root, disjointSet.find(3))
            assertEquals(root, disjointSet.find(4))
        }
    }

    @Nested
    @DisplayName("Union")
    inner class Union {

        @Test
        fun `Merges two singleton sets`() {
            val universe = listOf(1, 2, 3)
            val disjointSet = DisjointSet(universe)

            disjointSet.union(1, 2)

            assertEquals(2, disjointSet.components.size)
            assertTrue(disjointSet.components.any { it.containsAll(listOf(1, 2)) })
            assertTrue(disjointSet.components.any { it == setOf(3) })
        }

        @Test
        fun `Multiple unions create larger sets`() {
            val universe = listOf(1, 2, 3, 4, 5)
            val disjointSet = DisjointSet(universe)

            disjointSet.union(1, 2)
            disjointSet.union(2, 3)
            disjointSet.union(4, 5)

            assertEquals(2, disjointSet.components.size)
            assertTrue(disjointSet.components.any { it.containsAll(listOf(1, 2, 3)) })
            assertTrue(disjointSet.components.any { it.containsAll(listOf(4, 5)) })
        }

        @Test
        fun `Union of elements already in same set is a no-op`() {
            val universe = listOf(1, 2, 3)
            val disjointSet = DisjointSet(universe)

            disjointSet.union(1, 2)
            val setsAfterFirstUnion = disjointSet.components.toSet()

            disjointSet.union(2, 1)
            val setsAfterSecondUnion = disjointSet.components.toSet()

            assertEquals(setsAfterFirstUnion, setsAfterSecondUnion)
        }

        @Test
        fun `Union merges two existing sets`() {
            val universe = listOf(1, 2, 3, 4, 5)
            val disjointSet = DisjointSet(universe)

            disjointSet.union(1, 2)
            disjointSet.union(3, 4)
            assertEquals(3, disjointSet.components.size)

            disjointSet.union(2, 3)
            assertEquals(2, disjointSet.components.size)
            assertTrue(disjointSet.components.any { it.containsAll(listOf(1, 2, 3, 4)) })
            assertTrue(disjointSet.components.any { it == setOf(5) })
        }

        @Test
        fun `Union by size attaches smaller tree to larger`() {
            val universe = listOf(1, 2, 3, 4, 5)
            val disjointSet = DisjointSet(universe)

            // Create a set of size 3
            disjointSet.union(1, 2)
            disjointSet.union(2, 3)

            // Create a set of size 2
            disjointSet.union(4, 5)

            // Union should attach smaller (4,5) to larger (1,2,3)
            disjointSet.union(1, 4)

            // All elements should now be in the same set
            assertEquals(1, disjointSet.components.size)
            assertTrue(disjointSet.components.first().containsAll(listOf(1, 2, 3, 4, 5)))
        }
    }
}
