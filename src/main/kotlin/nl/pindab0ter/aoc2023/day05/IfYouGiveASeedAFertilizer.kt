package nl.pindab0ter.aoc2023.day05

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.lib.collections.tail
import nl.pindab0ter.lib.timing

fun main() {
    val almanac = Almanac(getInput(2023, 5))

    timing {
        val lowestLocationNumberPartOne = findFirstSeedSingles(almanac.partOneSeeds, almanac.maps)
        println("Lowest location number, part one: $lowestLocationNumberPartOne")
    }

    timing("Reverse search") {
        val lowestLocationNumberPartTwo = findFirstSeedRanges(almanac.partTwoSeeds, almanac.maps)
        println("Lowest location number, part two: $lowestLocationNumberPartTwo")
    }
}

typealias Map = Set<Range>
typealias Range = Pair<ULongRange, ULongRange>

data class Almanac(
    val partOneSeeds: Set<ULong>,
    val partTwoSeeds: Set<ULongRange>,
    val maps: Set<Map>,
) {
    companion object {
        operator fun invoke(input: String): Almanac {
            val sections = input.split("\n\n")

            val partOneSeeds = sections.first()
                .split(":").last().trim()
                .split(" ").map(String::toULong)
                .toSet()

            val partTwoSeeds = partOneSeeds
                .chunked(2) { (start, length) -> (start until start + length) }
                .toSet()

            val maps = sections.tail().map { map ->
                map.split(":").last().trim()
                    .splitToSequence("\n")
                    .map { line ->
                        val (destination, source, length) = line.split(" ").map(String::toULong)
                        (destination until destination + length) to (source until source + length)
                    }.toSet()
            }.toSet()

            return Almanac(partOneSeeds, partTwoSeeds, maps)
        }
    }
}

fun applyMapReverse(location: ULong, map: Map): ULong = map
    .firstOrNull { (destination, _) ->
        location in destination
    }?.let { (destination, source) ->
        (source.first - destination.first) + location
    } ?: location

tailrec fun findFirstSeedSingles(seeds: Set<ULong>, maps: Set<Map>, location: ULong = 1u): ULong {
    val seed = maps.reversed().fold(location) { acc, map ->
        applyMapReverse(acc, map)
    }

    if (seed in seeds) return location

    return findFirstSeedSingles(seeds, maps, location + 1u)
}

tailrec fun findFirstSeedRanges(seeds: Set<ULongRange>, maps: Set<Map>, location: ULong = 1u): ULong {
    val seed = maps.reversed().fold(location) { acc, map ->
        applyMapReverse(acc, map)
    }

    if (seeds.any { it.contains(seed) }) return location

    return findFirstSeedRanges(seeds, maps, location + 1u)
}
