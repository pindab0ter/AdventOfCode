package nl.pindab0ter.aoc2023.day14

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.aoc2023.day14.Direction.*
import nl.pindab0ter.aoc2023.day14.RockType.ROUND
import nl.pindab0ter.aoc2023.day14.RockType.SQUARE
import nl.pindab0ter.lib.println
import nl.pindab0ter.lib.timing
import java.util.*

fun main() {
    val input = getInput(2023, 14)
    val rocks = parse(input)

    val tiltedRocks = tilt(rocks, EAST)
    println("The total load on the north support beams after tilting: ${calculateLoad(tiltedRocks)}")

    timing {
        // Example input with 10,000 cycles takes ±1s, so this doesn't scale.
        val cycledRocks = (1..1_000_000_000).fold(rocks.toSortedSet(NORTH.comparator)) { acc, _ -> spinCycle(acc) }
        println("\nThe total load on the north support beams after 1 billion cycles: ${calculateLoad(cycledRocks)}")
    }
}

enum class Direction {
    NORTH {
        override val comparator: Comparator<Rock> = compareBy<Rock> { it.y }.thenBy { it.x }
    },
    EAST {
        override val comparator: Comparator<Rock> = compareByDescending<Rock> { it.x }.thenBy { it.y }
    },
    SOUTH {
        override val comparator: Comparator<Rock> = compareByDescending<Rock> { it.y }.thenByDescending { it.x }
    },
    WEST {
        override val comparator: Comparator<Rock> = compareByDescending<Rock> { it.y }.thenBy { it.x }
    };

    abstract val comparator: Comparator<Rock>
}

fun spinCycle(rocks: Set<Rock>): SortedSet<Rock> {
    return tilt(rocks, NORTH)
        .run { tilt(this, WEST) }
        .run { tilt(this, SOUTH) }
        .run { tilt(this, EAST) }
}

fun tilt(rocks: Set<Rock>, direction: Direction): SortedSet<Rock> = rocks
    .toSortedSet(direction.comparator)
    .fold(setOf<Rock>()) { acc, rock ->
        val maxX = rocks.maxOf(Rock::x)
        val maxY = rocks.maxOf(Rock::y)

        when (rock.type) {
            SQUARE -> acc + rock
            ROUND -> {
                val nextRock = acc.lastOrNull {
                    when (direction) {
                        NORTH -> it.x == rock.x && it.y < rock.y
                        EAST -> it.x > rock.x && it.y == rock.y
                        SOUTH -> it.x == rock.x && it.y > rock.y
                        WEST -> it.x < rock.x && it.y == rock.y
                    }
                }

                acc + when (direction) {
                    NORTH -> rock.copy(y = nextRock?.y?.plus(1) ?: 0)
                    EAST -> rock.copy(x = nextRock?.x?.minus(1) ?: maxX)
                    SOUTH -> rock.copy(y = nextRock?.y?.minus(1) ?: maxY)
                    WEST -> rock.copy(x = nextRock?.x?.plus(1) ?: 0)
                }
            }
        }
    }
    .toSortedSet(NORTH.comparator)

fun calculateLoad(rocks: SortedSet<Rock>): Int {
    val roundRocks = rocks.filter { it.type == ROUND }.toSet()
    fun Rock.weight(): Int = rocks.maxOf(Rock::y) + 1 - y

    /**
     * The [rocks] need to be sorted by `x`, then `y`
     */
    tailrec fun weightOf(rocks: Set<Rock>, rock: Rock, acc: Int = 0): Int {
        val remainingRocks = rocks.minus(rock)
        val rockBelow = rocks.firstOrNull { it.y == rock.y + 1 }

        if (remainingRocks.isEmpty()) return acc + rock.weight()

        if (rockBelow != null) return weightOf(
            rocks = remainingRocks.minus(rockBelow),
            rock = rockBelow,
            acc = acc + rock.weight()
        )

        return weightOf(
            rocks = remainingRocks,
            rock = remainingRocks.first(),
            acc = acc + rock.weight()
        )
    }

    return weightOf(roundRocks, roundRocks.first())
}
