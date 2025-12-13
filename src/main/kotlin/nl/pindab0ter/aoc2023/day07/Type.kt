package nl.pindab0ter.aoc2023.day07

import nl.pindab0ter.lib.collections.grouped
import kotlin.enums.enumEntries

enum class Type {
    FIVE_OF_A_KIND {
        /**
         * A five of a kind is either one group of five cards or two groups of cards, one of which is jokers.
         */
        override fun matchesWithJokers(cards: CharSequence) = cards.grouped().let { groups ->
            groups.size == 1 || (groups.size == 2 && groups.any { JOKER in it })
        }

        override fun matchesWithoutJokers(cards: CharSequence) = cards.grouped().size == 1
    },
    FOUR_OF_A_KIND {
        /**
         * A four of a kind is either:
         *  - one group of four cards
         *  - three groups of cards, one of which is jokers and the other is only one card
         */
        override fun matchesWithJokers(cards: CharSequence) = cards.grouped().let { groups ->
            val groupWithJokers = groups.find { JOKER in it }

            groups.any { it.size == 4 } ||
                    (groups.size == 3 &&
                            groupWithJokers !== null &&
                            groups.minusElement(groupWithJokers).any { it.size == 1 })
        }

        override fun matchesWithoutJokers(cards: CharSequence) = cards.grouped().any { it.size == 4 }
    },
    FULL_HOUSE {
        /**
         * A full house is either:
         * - one group of three cards and one group of two cards
         * - three groups of cards, one of which contains jokers
         *
         * The latter rule could match [FOUR_OF_A_KIND]s, but they get matched first
         */
        override fun matchesWithJokers(cards: CharSequence) = cards.grouped().let { groups ->
            (groups.any { it.size == 3 } && groups.any { it.size == 2 }) ||
                    (groups.size == 3 && groups.any { JOKER in it })
        }

        override fun matchesWithoutJokers(cards: CharSequence) = cards.grouped().size == 2
    },
    THREE_OF_A_KIND {
        /**
         * A three of a kind either:
         * - contains a group of three cards
         * - contains a group of two cards and one joker
         * - consists of four groups, one of which contains two jokers
         *
         * The first rule could match [FULL_HOUSE], but those get matched first
         */
        override fun matchesWithJokers(cards: CharSequence) = cards.grouped().let { groups ->
            groups.any { it.size == 3 } ||
                    (groups.any { it.size == 2 } && groups.any { JOKER in it && it.size == 1 }) ||
                    (groups.size == 4 && groups.any { JOKER in it && it.size == 2 })
        }

        override fun matchesWithoutJokers(cards: CharSequence) = cards.grouped().any { it.size == 3 }
    },
    TWO_PAIR {
        /**
         * A two pair is either:
         * - three groups
         * - four groups, one of which contains jokers
         *
         * The second rule could match a [THREE_OF_A_KIND] if the group of two consists of jokers, but that gets matched first.
         */
        override fun matchesWithJokers(cards: CharSequence) = cards.grouped().let { groups ->
            groups.size == 3 || (groups.size == 4 && groups.any { JOKER in it })
        }

        override fun matchesWithoutJokers(cards: CharSequence) = cards.grouped().size == 3
    },
    ONE_PAIR {
        /**
         * A one pair is either
         * - four groups, none of which are jokers
         * - five groups, one of which is a joker
         */
        override fun matchesWithJokers(cards: CharSequence) = cards.grouped().let { groups ->
            (groups.size == 4 && groups.none { JOKER in it }) ||
                    (groups.size == 5 && groups.any { JOKER in it })
        }

        override fun matchesWithoutJokers(cards: CharSequence) = cards.grouped().size == 4
    },
    HIGH_CARD {
        /**
         * A high card is a hand that doesn't match any of the other types.
         */
        override fun matchesWithJokers(cards: CharSequence) = true
        override fun matchesWithoutJokers(cards: CharSequence) = true
    };

    abstract fun matchesWithJokers(cards: CharSequence): Boolean
    abstract fun matchesWithoutJokers(cards: CharSequence): Boolean

    @OptIn(ExperimentalStdlibApi::class)
    companion object {
        private const val JOKER = 'J'
        fun of(hand: CharSequence, withJokers: Boolean): Type = enumEntries<Type>().first {
            when (withJokers) {
                true -> it.matchesWithJokers(hand)
                false -> it.matchesWithoutJokers(hand)
            }
        }
    }
}
