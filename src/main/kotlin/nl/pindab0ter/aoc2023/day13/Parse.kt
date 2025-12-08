package nl.pindab0ter.aoc2023.day13

import nl.pindab0ter.lib.Grid

fun parse(input: String): List<Grid<Surface>> = input
    .split("\n\n")
    .map { patternString ->
        patternString.lines().map { line ->
            line.mapNotNull(Surface::from)
        }
    }
    .map(::Grid)
