package nl.pindab0ter.aoc2023.day04

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.lib.timing
import kotlin.math.pow

fun main() {
    val cards = parse(getInput(2023, 4))

    timing {
        val totalScore = cards.sumOf(ScratchCard::score)
        println("Combined score of all cards: $totalScore")
    }

    timing {
        val winnings = play(listOf(cards), cards)
        println("\nWinnings: ${winnings.flatten().count()}")
    }
}

typealias Hand = List<ScratchCard>
typealias CardNumber = Int

data class ScratchCard(
    val number: Int,
    val winningNumbers: List<Number>,
    val candidateNumbers: List<Number>,
) {
    /**
     * @return The amount of winning numbers on this scratchcard.
     */
    private fun value(): Int {
        return candidateNumbers.count { winningNumbers.contains(it) }
    }

    fun score(): Int {
        return 2.toDouble().pow((value() - 1).toDouble()).toInt()
    }

    /**
     * Determine the numbers of the scratchcards that have been won.
     * You win an amount of scratchcards equal to the amount of winning numbers on the scratchcard.
     *
     * The cards you win are the cards with the numbers that are equal to the number of the scratchcard that is
     * being played, plus the amount of winning numbers on the scratchcard.
     *
     * @return The numbers of the scratchcards that have been won.
     */
    fun determineWinnings(): List<CardNumber> = when {
        value() == 0 -> listOf()
        else -> (number + 1..number + value()).toList()
    }
}

val cardRegex = Regex("""^Card\s*(\d+): ([\s\d]+) \| ([\s\d]+)$""")
val spaceRegex = Regex("""\s{1,2}""")

fun parse(input: String): List<ScratchCard> = input.lines().map(::parseLine)

fun parseLine(line: String): ScratchCard {
    val matchResult = cardRegex.find(line)
    val (number, winningNumbers, candidateNumbers) = matchResult!!.destructured

    return ScratchCard(
        number.toInt(),
        winningNumbers.trim().split(spaceRegex).map(String::toInt).toList(),
        candidateNumbers.trim().split(spaceRegex).map(String::toInt).toList(),
    )
}


/**
 * Play a game of scratchcards.
 *
 * @param hands The [hands] of scratchcards that have been played so far. The last hand is the hand that will be played.
 * @param givenCards The initial set of cards that have been given to the player. Used to find the won cards.
 * @return The [hands] of scratchcards that have been played including the latest winnings.
 */
tailrec fun play(hands: List<Hand>, givenCards: Hand): List<Hand> {
    val newlyWonCards: Hand = hands
        .last()
        .flatMap(ScratchCard::determineWinnings)
        .map { cardNumber -> givenCards.first { card -> card.number == cardNumber } }

    return if (newlyWonCards.isNotEmpty()) {
        play(hands.plusElement(newlyWonCards), givenCards)
    } else {
        hands
    }
}
