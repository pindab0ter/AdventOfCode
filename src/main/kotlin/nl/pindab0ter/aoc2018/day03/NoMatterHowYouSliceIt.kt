package nl.pindab0ter.aoc2018.day03

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.aoc2018.day03.Claim.Factory

fun main() = getInput(2018, 3)
    .lines()
    .map(Factory::fromString)
    .let { claims ->
        print(
            """
            --- Day 3: No Matter How You Slice It ---

            Part one: How many square inches of fabric are within two or more claims?
            ${contestedAreas(claims)}

            Part two: What is the ID of the only claim that doesn't overlap?
            ${findNonOverlappingClaim(claims)}
            
            """.trimIndent()
        )
    }

fun contestedAreas(claims: List<Claim>): Int = claims
    .flatMap { it.area.occupies }
    .groupingBy { it }
    .eachCount()
    .count { it.value > 1 }

fun findNonOverlappingClaim(claims: List<Claim>): Claim = claims.first { claim ->
    claims.minus(claim).none { it.overlapsWith(claim) }
}
