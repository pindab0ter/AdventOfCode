package nl.pindab0ter.aoc2018.day09

import nl.pindab0ter.aoc.getInput
import java.util.*
import kotlin.math.absoluteValue

fun main() = getInput(2018, 9)
    .let { input ->
        Regex("""(\d+).*?(\d+)""")
            .find(input)!!
            .destructured
            .toList()
            .map(String::toInt)
    }
    .let { (players, lastMarble) ->
        print(
            """
            --- Day 9: Marble Mania ---

            Part one: What is the winning Elf's score?
            ${MarbleMania(players, lastMarble).play()}

            Part two: What would the new winning Elf's score be if the number of the last marble were 100 times larger?
            ${MarbleMania(players, lastMarble * 100).play()}

            """.trimIndent()
        )
    }

// Solution based on Todd Ginsberg’s
// https://todd.ginsberg.com/post/advent-of-code/2018/day9/#d9p1
class MarbleMania(private val players: Int, private val lastMarble: Int) {
    private val marbles = ArrayDeque<Int>().also { it.add(0) }
    private val scores = LongArray(players)

    fun play(): Long = (1..lastMarble).forEach { marble ->
        when {
            marble % 23 == 0 -> {
                scores[marble % players] += marble + with(marbles) {
                    shift(-7)
                    removeFirst().toLong()
                }
                marbles.shift(1)
            }

            else -> {
                with(marbles) {
                    shift(1)
                    addFirst(marble)
                }
            }
        }
    }.let { scores.max() }
}

private fun <E> Deque<E>.shift(amount: Int) = when {
    amount < 0 -> repeat(amount.absoluteValue) { addLast(removeFirst()) }
    else -> repeat(amount.absoluteValue) { addFirst(removeLast()) }
}

