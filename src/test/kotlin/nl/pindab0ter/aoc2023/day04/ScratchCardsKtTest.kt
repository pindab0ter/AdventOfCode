package nl.pindab0ter.aoc2023.day04

import nl.pindab0ter.lib.assertAllEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("2023 Day 04 - Scratchcards")
class ScratchCardsKtTest {

    //////////////
    // Part One //
    //////////////

    @Test
    fun `Calculate card score`() = assertAllEquals(
        8 to parseLine("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53").score(),
        2 to parseLine("Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19").score(),
        2 to parseLine("Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1").score(),
        1 to parseLine("Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83").score(),
        0 to parseLine("Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36").score(),
        0 to parseLine("Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11").score(),
    )

    @Test
    fun `Total points`() {
        val input = """
            Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
            Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
            Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
            Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
            Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
            Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
        """.trimIndent()
        val value = input.lines().map(::parseLine).sumOf(ScratchCard::score)
        Assertions.assertEquals(13, value)
    }

    //////////////
    // Part Two //
    //////////////

    @Test
    fun `Determine winnings of card 1`() {
        val input = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
        val card = parseLine(input)
        Assertions.assertEquals(listOf(2, 3, 4, 5), card.determineWinnings())
    }

    @Test
    fun `Determine amount of won scratchcards`() {
        val input = """
            Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
            Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
            Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
            Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
            Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
            Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
        """.trimIndent()
        val cards = input.lines().map(::parseLine)
        val amountOfCards = play(listOf(cards), cards).flatten().count()

        Assertions.assertEquals(30, amountOfCards)
    }

}
