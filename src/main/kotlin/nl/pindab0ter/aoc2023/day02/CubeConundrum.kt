package nl.pindab0ter.aoc2023.day02

import nl.pindab0ter.aoc.getInput

fun main() {
    val games = parse(getInput(2023, 2))

    val idSum = games.filter(::enoughCubesAreAvailable).sumOf { game -> game.id }
    println("Sum of valid game IDs: $idSum")

    val sumOfPowers = games.sumOf(::powerOfMinimalRequiredCubes)
    println("\nSum of power of minimal required cubes: $sumOfPowers")
}

val availableCubes = mapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14,
)

data class Game(
    val id: Int = 0,
    val hands: List<Cubes> = emptyList(),
)

data class Cubes(
    val red: Int = 0,
    val green: Int = 0,
    val blue: Int = 0,
)

fun parse(input: String): List<Game> = input.lines().map(::parseLine)

fun parseLine(line: String): Game {
    val gameRegex = Regex("""Game (\d+): """)
    val gameNumber = gameRegex.find(line)!!.groupValues[1].toInt()

    val handStrings = line.split(": ").last().split("; ")
    val handRegex = Regex("""(\d+) (\w+)""")

    val hands = handStrings.map { handString ->
        val handRegexResults = handRegex.findAll(handString)
        val hand = handRegexResults.fold(Cubes()) { hand, result ->
            val (cubes, color) = result.destructured
            val newHand = when (color) {
                "red" -> hand.copy(red = hand.red + cubes.toInt())
                "green" -> hand.copy(green = hand.green + cubes.toInt())
                "blue" -> hand.copy(blue = hand.blue + cubes.toInt())
                else -> throw IllegalArgumentException("Unknown color: $color")
            }
            return@fold newHand
        }

        return@map hand
    }

    return Game(gameNumber, hands)
}

fun enoughCubesAreAvailable(game: Game): Boolean {
    return !game.hands.any { hand ->
        hand.red > availableCubes["red"]!! ||
                hand.green > availableCubes["green"]!! ||
                hand.blue > availableCubes["blue"]!!
    }
}

fun powerOfMinimalRequiredCubes(game: Game): Int {
    val cubes = Cubes(
        red = game.hands.maxOf(Cubes::red),
        green = game.hands.maxOf(Cubes::green),
        blue = game.hands.maxOf(Cubes::blue),
    )

    return cubes.red * cubes.green * cubes.blue
}
