package nl.pindab0ter.aoc2023.day10

import com.github.ajalt.mordant.rendering.TextColors.*
import nl.pindab0ter.common.*

data class Maze(val sections: List<List<Section?>>, val startCoordinates: Coordinate) {
    private val start: Section = sections[startCoordinates]!!
    private val loopSections: Set<Coordinate>
    val tilesEnclosedByLoop: Set<Coordinate>
    val furthestDistanceFromStart: Int

    init {
        val (coordinates, distance) = determineLoopAndFurthestDistance()

        loopSections = coordinates
        furthestDistanceFromStart = distance

        tilesEnclosedByLoop = determineTilesEnclosedByLoop()
    }

    private fun directionFor(
        coordinate: Coordinate,
        comingFrom: Direction,
    ) = sections.getOrNull(coordinate)?.directions!!.minus(comingFrom.opposite()).first()

    private fun determineLoopAndFurthestDistance(): Pair<Set<Coordinate>, Int> {
        fun List<Pair<Coordinate, Direction>>.coordinates() = map(Pair<Coordinate, Direction>::first)

        tailrec fun calculateDistance(
            steps: List<Pair<Coordinate, Direction>>,
            loopSections: Set<Coordinate>,
            distance: Int,
        ): Pair<Set<Coordinate>, Int> {
            val nextSteps = steps.map { (coordinates, origin) ->
                val direction = directionFor(coordinates, origin)
                Coordinate(coordinates.x + direction.dx, coordinates.y + direction.dy) to direction
            }

            return when {
                steps.coordinates().allElementsEqual() && distance > 0 -> loopSections to distance
                else -> {
                    calculateDistance(
                        steps = nextSteps,
                        loopSections = loopSections.plus(nextSteps.map { (coordinates, _) -> coordinates }.toSet()),
                        distance = distance + 1
                    )
                }
            }
        }

        // Start with all directions pointing away from the start
        val initialSteps = start.directions.map { direction ->
            startCoordinates to direction.opposite()
        }

        return calculateDistance(initialSteps, setOf(startCoordinates), 0)
    }

    private fun determineTilesEnclosedByLoop(): Set<Coordinate> = sections.withIndex().flatMap { (y, column) ->
        column.indices.map { x -> if (isEnclosedByLoop(x, y)) Coordinate(x, y) else null }
    }.filterNotNull().toSet()

    private fun isEnclosedByLoop(x: Int, y: Int): Boolean {
        val isNotPartOfLoop = !loopSections.contains(x, y)
        val isEnclosedByLoop = (0..x).count { rayX ->
            loopSections.contains(rayX, y) && sections[rayX, y]!!.directions.contains(Direction.NORTH)
        }.isOdd()
        return (isNotPartOfLoop && isEnclosedByLoop)
    }

    override fun toString(): String = buildString {
        sections.forEachIndexed { y, column ->
            column.forEachIndexed { x, section ->
                when {
                    startCoordinates.x == x.toLong() && startCoordinates.y == y.toLong() -> append(red("●"))
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
            val startCoordinates = grid.coordinateOfFirst { it == 'S' }!!

            return Maze(sections = grid.mapIndexed { y, row ->
                row.mapIndexed { x, character ->
                    when (character) {
                        'S' -> {
                            val directions = Direction.getDirectionsPointingTo(Coordinate(x, y), grid)
                            Section.from(directions.map(Direction::opposite).toSet())
                        }

                        else -> Section.from(character)
                    }
                }
            }, startCoordinates)
        }
    }
}
