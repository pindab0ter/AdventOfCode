package nl.pindab0ter.aoc2023.day12

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.aoc2023.day12.Spring.*
import nl.pindab0ter.lib.collections.mapAsync
import nl.pindab0ter.lib.println
import nl.pindab0ter.lib.timing

fun main() {
    val input = getInput(2023, 12)
    val records = parse(input)

    timing {
        val sumOfArrangements = records.sumOf(Record::countArrangements)
        println("Sum of arrangements: $sumOfArrangements")
    }

    timing {
        val sumOfArrangements = records.map(Record::unfold).mapAsync(Record::countArrangements).sum()
        println("\nSum of arrangements (unfolded): $sumOfArrangements")
    }
}

typealias Group = Int
typealias Groups = List<Group>

data class Record(val springs: List<Spring>, val groups: Groups) {
    private val cache: HashMap<Record, Long> = hashMapOf()

    fun unfold() = copy(
        springs = (1..5).flatMap { springs + UNKNOWN }.dropLast(1),
        groups = (1..5).flatMap { groups }
    )

    fun countArrangements(
        springs: List<Spring> = this.springs,
        groups: Groups = this.groups,
        acc: Long = 0L,
    ): Long {
        if (groups.isEmpty()) return if (DAMAGED in springs) 0 else acc + 1
        if (springs.isEmpty()) return acc

        fun operationalSpring() = countArrangements(springs.drop(1), groups)
        fun damagedSpring(): Long {
            val group = groups.first()
            return if (
                group <= springs.size &&               // Enough springs remain to satisfy group
                OPERATIONAL !in springs.take(group) && // The group doesn't contain operational springs
                (springs.size == group ||              // These are the last remaining springs, or
                        springs[group] != DAMAGED)     // The spring after this group is not damaged
            ) countArrangements(springs.drop(group + 1), groups.drop(1)) else 0
        }

        return cache.getOrPut(Record(springs, groups)) {
            when (springs.first()) {
                OPERATIONAL -> operationalSpring()
                DAMAGED -> damagedSpring()
                UNKNOWN -> operationalSpring() + damagedSpring()
            }
        }
    }

    override fun toString(): String = buildString {
        append(springs.joinToString(""))
        append(" ")
        append(groups.joinToString(""))
    }
}

