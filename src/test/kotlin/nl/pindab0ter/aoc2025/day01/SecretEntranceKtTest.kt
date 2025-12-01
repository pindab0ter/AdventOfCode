package nl.pindab0ter.aoc2025.day01

import nl.pindab0ter.aoc2025.day01.Instruction.Direction.LEFT
import nl.pindab0ter.aoc2025.day01.Instruction.Direction.RIGHT
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.Test
import kotlin.test.assertEquals

@DisplayName("2025 Day 01 - Secret Entrance")
class SecretEntranceKtTest {
    @ParameterizedTest(name = "{0} turn {1} → {2}")
    @MethodSource("turnDialProvider")
    fun `Turn dial`(position: Int, instruction: Instruction, expectedPosition: Int, expectedTimesPointedToZero: Int) {
        val turnedDial = Dial(position).turn(instruction)

        assertEquals(expectedPosition, turnedDial.position)
        assertEquals(expectedTimesPointedToZero, turnedDial.timesPointedToZero)
    }

    @Test
    fun `Part one`() {
        val directions = """
            L68
            L30
            R48
            L5
            R60
            L55
            L1
            L99
            R14
            L82""".trimIndent().parse()

        val dialLog = followDirections(Dial(50), directions)

        val actual = dialLog.count { it.position == 0 }

        assertEquals(3, actual)
    }

    @Test
    fun `Part two`() {
        val directions = """
            L68
            L30
            R48
            L5
            R60
            L55
            L1
            L99
            R14
            L82""".trimIndent().parse()

        val dialLog = followDirections(Dial(50), directions)

        val actual = dialLog.last().timesPointedToZero

        assertEquals(6, actual)
    }

    companion object {
        @JvmStatic
        fun turnDialProvider(): Stream<Arguments> = Stream.of(
            arguments(11, Instruction(RIGHT, 8), 19, 0),
            arguments(19, Instruction(LEFT, 19), 0, 1),
            arguments(0, Instruction(LEFT, 1), 99, 0),
            arguments(99, Instruction(RIGHT, 1), 0, 1),
            arguments(50, Instruction(RIGHT, 200), 50, 2),
            arguments(50, Instruction(LEFT, 200), 50, 2),
            arguments(99, Instruction(RIGHT, 101), 0, 2),
            arguments(0, Instruction(LEFT, 101), 99, 1),
            arguments(50, Instruction(RIGHT, 1000), 50, 10),
        )
    }
}
