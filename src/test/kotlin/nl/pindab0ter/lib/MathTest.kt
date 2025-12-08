package nl.pindab0ter.lib

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Math")
class MathTest {
    @Test
    fun `Calculate the Greatest Common Divisor`() = assertAllEquals(
        6L to gcd(18L, 48L),
        1L to gcd(17L, 5L),
        9L to gcd(27L, 18L),
        15L to gcd(75L, 45L),
    )

    @Test
    fun `Calculate the Lowest Common Multiple`() = assertAllEquals(
        6L to lcm(2L, 3L),
        12L to lcm(3L, 4L),
        15L to lcm(5L, 3L),
        35L to lcm(7L, 5L),
        72L to lcm(24L, 18L),
        35L to lcm(7L, 5L, 5L),
        72L to lcm(24L, 18L, 12L),
    )

    @Test
    fun `Get the number of digits in a ULong`() = assertAllEquals(
        1 to 1uL.digits,
        2 to 10uL.digits,
        2 to 19uL.digits,
        20 to ULong.MAX_VALUE.digits,
    )
}
