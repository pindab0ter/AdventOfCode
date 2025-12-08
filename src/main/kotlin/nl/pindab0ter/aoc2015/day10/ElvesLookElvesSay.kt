package nl.pindab0ter.aoc2015.day10

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.lib.head
import nl.pindab0ter.lib.iterate
import nl.pindab0ter.lib.tail
import nl.pindab0ter.lib.timing

fun lookAndSay(sequence: List<Int>): List<Int> {
    val newSequence = arrayListOf<Int>()
    var count = 1
    var number = sequence.head()

    sequence.tail().forEach { element ->
        if (element == number) count++
        else {
            newSequence.add(count)
            newSequence.add(number)
            number = element
            count = 1
        }
    }

    newSequence.add(count)
    newSequence.add(number)

    return newSequence
}

fun main() {
    val sequence = getInput(2015, 10).map(Char::digitToInt)

    val partOne = timing("Iterating 40 times") { sequence.iterate(::lookAndSay).elementAt(40).size }
    println("The length of the sequence after 40 iterations is: $partOne\n")

    val partTwo = timing("Iterating 50 times") { sequence.iterate(::lookAndSay).elementAt(50).size }
    println("The length of the sequence after 50 iterations is: $partTwo")
}
