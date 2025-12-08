package nl.pindab0ter.aoc2018.day08

import nl.pindab0ter.aoc.getInput

fun main() = getInput(2018, 8)
    .split(" ")
    .map(String::toInt)
    .iterator()
    .let(Node::invoke)
    .let { node ->
        print(
            """
            --- Day 8: Memory Maneuver ---

            Part one: What is the sum of all metadata entries?
            ${node.dataSum}

            Part two: What is the value of the root node?
            ${node.value}

            """.trimIndent()
        )
    }

data class Node(val children: List<Node>, val data: List<Int>) {
    val dataSum: Int
        get() = children.sumOf { it.dataSum } + data.sum()
    val value: Int
        get() =
            if (children.isEmpty()) data.sum()
            else data.sumOf { children.getOrNull(it - 1)?.value ?: 0 }

    companion object {
        operator fun invoke(input: Iterator<Int>): Node {
            val childrenSize = input.next()
            val dataSize = input.next()

            val children: List<Node> = (0 until childrenSize).map { invoke(input) }
            val data: List<Int> = (0 until dataSize).map { input.next() }.toList()

            return Node(children, data)
        }
    }
}
