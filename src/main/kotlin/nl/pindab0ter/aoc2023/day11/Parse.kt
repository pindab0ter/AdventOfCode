package nl.pindab0ter.aoc2023.day11

import nl.pindab0ter.lib.types.Coordinate

fun parse(input: String): Set<Coordinate> = input.lines().flatMapIndexed { y, line ->
    line.mapIndexedNotNull { x, c ->
        if (c == '#') Coordinate(x, y)
        else null
    }
}.toSet()
