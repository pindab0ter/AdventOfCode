package nl.pindab0ter.aoc2023.day03

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.lib.timing

fun main() {
    val input = getInput(2023, 3)

    timing {
        val partNumbers = getPartNumbers(input)
        println("Sum of all part numbers: ${partNumbers.sum()}")
    }

    timing {
        val gearRatios = getGearRatios(input)
        println("\nSum of all gear ratios: ${gearRatios.sum()}")
    }
}

typealias Schematic = String
typealias PartNumber = Int

val numberRegex = Regex("""\d+""")
val symbolRegex = Regex("""[^\d\s.]""")
val asteriskRegex = Regex("""\*""")

fun getPartNumbers(schematic: Schematic): List<PartNumber> {
    val schematicWidth = schematic.indexOf('\n')
    val numbers = numberRegex.findAll(schematic).toList()
    val symbols = symbolRegex.findAll(schematic).toList()

    return numbers
        .filter { number ->
            symbols.any { symbol -> number.isAdjacentTo(symbol, schematicWidth) }
        }
        .map { number -> number.value.toInt() }
}

fun getGearRatios(schematic: Schematic): List<Int> {
    val schematicWidth = schematic.indexOf('\n')
    val numbers = numberRegex.findAll(schematic).toList()
    val asterisks = asteriskRegex.findAll(schematic).toList()

    return asterisks
        // Find all numbers that are next to asterisks
        .map { asterisk ->
            numbers.filter { it.isAdjacentTo(asterisk, schematicWidth) }
        }
        // Any instance of two numbers next to an asterisk is a gear
        .filter { numbersNextToAsterisk ->
            numbersNextToAsterisk.count() == 2
        }
        // Multiply the gears to get their ‘ratio’
        .map { gears ->
            gears
                .map { gear -> gear.value.toInt() }
                .reduce { acc, gear -> acc * gear }
        }
}

fun MatchResult.isAdjacentTo(other: MatchResult, lineLength: Int): Boolean {
    val widenedRange = range.first - 1..range.last + 1

    val toTheLeft = widenedRange.first == other.range.last
    val toTheRight = widenedRange.last == other.range.first
    val above = widenedRange.any { it == other.range.first + (lineLength + 1) }
    val below = widenedRange.any { it == other.range.first - (lineLength + 1) }

    return above || below || toTheLeft || toTheRight
}
