package nl.pindab0ter.aoc2023.day11

import nl.pindab0ter.lib.types.Point

fun parse(input: String): Set<Point> = input.lines().flatMapIndexed { y, line ->
    line.mapIndexedNotNull { x, c ->
        if (c == '#') Point(x, y)
        else null
    }
}.toSet()
