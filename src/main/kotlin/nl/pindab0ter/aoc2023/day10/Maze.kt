package nl.pindab0ter.aoc2023.day10

import com.github.ajalt.mordant.rendering.TextColors.*
import nl.pindab0ter.lib.*
import nl.pindab0ter.lib.collections.allElementsEqual
import nl.pindab0ter.lib.collections.contains
import nl.pindab0ter.lib.collections.pointOfFirst
import nl.pindab0ter.lib.collections.get
import nl.pindab0ter.lib.collections.getOrNull
import nl.pindab0ter.lib.types.Point
import nl.pindab0ter.lib.types.Direction
import nl.pindab0ter.lib.types.Direction.*
import kotlin.collections.map

data class Maze(val sections: List<List<Section?>>, val startingPoint: Point) {
    private val start: Section = sections[startingPoint]!!
    private val loopSections: Set<Point>
    val tilesEnclosedByLoop: Set<Point>
    val furthestDistanceFromStart: Int

    init {
        val (points, distance) = determineLoopAndFurthestDistance()

        loopSections = points
        furthestDistanceFromStart = distance

        tilesEnclosedByLoop = determineTilesEnclosedByLoop()
    }

    private fun directionFor(
        point: Point,
        comingFrom: Direction,
    ) = sections.getOrNull(point)?.directions!!.minus(comingFrom.opposite()).first()

    private fun determineLoopAndFurthestDistance(): Pair<Set<Point>, Int> {
        fun List<Pair<Point, Direction>>.points() = map(Pair<Point, Direction>::first)

        tailrec fun calculateDistance(
            steps: List<Pair<Point, Direction>>,
            loopSections: Set<Point>,
            distance: Int,
        ): Pair<Set<Point>, Int> {
            val nextSteps = steps.map { (point, origin) ->
                val direction = directionFor(point, origin)
                point.translate(direction) to direction
            }

            return when {
                steps.points().allElementsEqual() && distance > 0 -> loopSections to distance
                else -> {
                    calculateDistance(
                        steps = nextSteps,
                        loopSections = loopSections.plus(nextSteps.map { (point, _) -> point }.toSet()),
                        distance = distance + 1
                    )
                }
            }
        }

        // Start with all directions pointing away from the start
        val initialSteps = start.directions.map { direction ->
            startingPoint to direction.opposite()
        }

        return calculateDistance(initialSteps, setOf(startingPoint), 0)
    }

    private fun determineTilesEnclosedByLoop(): Set<Point> = sections.withIndex().flatMap { (y, column) ->
        column.indices.map { x -> if (isEnclosedByLoop(x, y)) Point(x, y) else null }
    }.filterNotNull().toSet()

    private fun isEnclosedByLoop(x: Int, y: Int): Boolean {
        val isNotPartOfLoop = !loopSections.contains(x, y)
        val isEnclosedByLoop = (0..x).count { rayX ->
            loopSections.contains(rayX, y) && sections[rayX, y]!!.directions.contains(NORTH)
        }.isOdd()
        return (isNotPartOfLoop && isEnclosedByLoop)
    }

    override fun toString(): String = buildString {
        sections.forEachIndexed { y, column ->
            column.forEachIndexed { x, section ->
                when {
                    startingPoint.x == x.toLong() && startingPoint.y == y.toLong() -> append(red("●"))
                    loopSections.contains(x, y) -> append(white(section!!.representation))
                    tilesEnclosedByLoop.contains(x, y) -> append(green("·"))
                    else -> append(black("·"))
                }
            }
            appendLine()
        }
    }

    companion object {
        fun from(input: String): Maze {
            val grid = input.lines().map(String::toList)
            val startingPoint = grid.pointOfFirst { it == 'S' }!!

            return Maze(sections = grid.mapIndexed { y, row ->
                row.mapIndexed { x, character ->
                    when (character) {
                        'S' -> Section.from(grid.pointingAwayFrom(Point(x, y)))

                        else -> Section.from(character)
                    }
                }
            }, startingPoint)
        }
    }
}

fun List<List<Char>>.pointingAwayFrom(
    point: Point,
): Set<Direction> = setOf(NORTH, EAST, SOUTH, WEST)
    .mapNotNull { direction ->
        val character = getOrNull(point.translate(direction))
        Section.from(character)?.directions?.firstOrNull { it == direction.opposite() }?.opposite()
    }
    .toSet()

