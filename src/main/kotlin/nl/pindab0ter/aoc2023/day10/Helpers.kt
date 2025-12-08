package nl.pindab0ter.aoc2023.day10

import nl.pindab0ter.lib.Coordinate


/**
 * @return `true` if the set contains the coordinate, `false` otherwise.
 */
fun Set<Coordinate>.contains(x: Long, y: Long) = contains(Coordinate(x, y))

/**
 * @return `true` if the set contains the coordinate, `false` otherwise.
 */
fun Set<Coordinate>.contains(x: Int, y: Int) = contains(x.toLong(), y.toLong())
