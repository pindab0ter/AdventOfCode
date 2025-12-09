package nl.pindab0ter.aoc2025.day07

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.lib.println


fun main() {
    val (count, outputLines) = getInput(2025, 7)
        .lines()
        .simulateTachyonBeamSplitting()

    println("The beam is split a total of $count times:\n$outputLines")
}

fun List<String>.simulateTachyonBeamSplitting(): TachyonBeamSplittingResult =
    generateSequence(SequenceData(0, 0, this.first())) { (index, count, previousLine) ->
        val nextIndex = index + 1
        var nextCount = count
        if (nextIndex >= this.count()) null
        else {
            val nextLine = StringBuilder(this[nextIndex])
            for (i in (0 until nextLine.length)) {
                if (nextLine[i] == '^' && (previousLine[i] == '|' || previousLine[i] == 'S')) {
                    nextCount++
                    nextLine[i - 1] = '|'
                    nextLine[i + 1] = '|'
                } else if (previousLine[i] == '|' || previousLine[i] == 'S') {
                    nextLine[i] = '|'
                }
            }

            SequenceData(nextIndex, nextCount, nextLine.toString())
        }
    }.let { items ->
        TachyonBeamSplittingResult(items.last().count, items.joinToString("\n") { sequenceData ->
            sequenceData.line
        })
    }

data class SequenceData(val index: Int, val count: Int, val line: String)
data class TachyonBeamSplittingResult(val count: Int, val visualization: String)
