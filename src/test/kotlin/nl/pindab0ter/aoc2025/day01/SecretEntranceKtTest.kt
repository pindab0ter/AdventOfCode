package nl.pindab0ter.aoc2025.day01

import nl.pindab0ter.aoc2025.day01.Rotation.Direction.LEFT
import nl.pindab0ter.aoc2025.day01.Rotation.Direction.RIGHT
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
    @ParameterizedTest(name = "{0} ⟲ {1} → {2}")
    @MethodSource("turnDialProvider")
    fun `Turn dial`(dial: Dial, rotation: Rotation, expected: Int) {
        val actual = dial.turn(rotation).position

        assertEquals(expected, actual)
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

        val dialLog = followDirections(Dial(50), directions);

        val actual = dialLog.count { it.position == 0 }

        assertEquals(3, actual)
    }

    @ParameterizedTest(name = "{1} → {0}")
    @MethodSource("partTwoProvider")
    fun `Part two`(expected: String, input: String) {
        val actual = TODO()

        assertEquals(expected, actual)
    }

    companion object {
        @JvmStatic
        fun turnDialProvider(): Stream<Arguments> = Stream.of(
            arguments(Dial(11), Rotation(RIGHT, 8), 19),
            arguments(Dial(19), Rotation(LEFT, 19), 0),
            arguments(Dial(0), Rotation(LEFT, 1), 99),
            arguments(Dial(99), Rotation(RIGHT, 1), 0),
            arguments(Dial(50), Rotation(RIGHT, 200), 50),
            arguments(Dial(50), Rotation(LEFT, 200), 50),
            arguments(Dial(99), Rotation(RIGHT, 101), 0),
            arguments(Dial(0), Rotation(LEFT, 101), 99),
        )

        @JvmStatic
        fun partTwoProvider(): Stream<Arguments> = Stream.of(
            arguments("expected", "actual"),
        )
    }
}
