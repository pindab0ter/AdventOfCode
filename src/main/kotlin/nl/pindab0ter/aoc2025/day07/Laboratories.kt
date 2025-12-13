package nl.pindab0ter.aoc2025.day07

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.lib.types.Coordinate
import nl.pindab0ter.lib.collections.Grid
import nl.pindab0ter.lib.println
import nl.pindab0ter.lib.collections.toGrid


fun main() {
    val input = getInput(2025, 7)

    val (count, outputLines) = input
        .lines()
        .simulateTachyonBeamSplitting()

    val possibleTrajectoryCount = input
        .toGrid()
        .countPossibleTrajectories()

    println("$outputLines\n")
    println("The beam is split a total of $count times")
    println("The tachyon particle would end up on $possibleTrajectoryCount different timelines")
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

fun Grid<Char>.countPossibleTrajectories(): ULong {
    val terminatingValues = setOf('^', null)
    val countCache = mutableMapOf<Coordinate, ULong>()

    fun countFrom(position: Coordinate): ULong {
        countCache[position]?.let { return it }

        var current = position

        while (getOrNull(current) !in terminatingValues) current = current.south()

        return when (getOrNull(current)) {
            '^' -> (countFrom(current.west()) + countFrom(current.east()))
                .also { countCache[position] = it }

            else -> 1uL
                .also { countCache[position] = it }
        }
    }

    return countFrom(coordinateOf('S'))
}

data class SequenceData(val index: Int, val count: Int, val line: String)
data class TachyonBeamSplittingResult(val count: Int, val visualization: String)
