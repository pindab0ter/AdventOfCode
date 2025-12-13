package nl.pindab0ter.lib

import kotlin.math.abs
import kotlin.math.pow

/** Returns the number of digits in this number. */
val ULong.digits: Int
    get() {
        var digits = 0
        var current = this

        while (current > 0u) {
            digits++
            current /= 10u
        }

        return digits
    }

/** Greatest common divisor. */
tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) abs(a) else gcd(b, a % b)

fun Int.isOdd(): Boolean = this % 2 != 0

/** Lowest common multiple. */
fun lcm(a: Long, b: Long): Long {
    return abs(a * b) / gcd(a, b)
}

/** Lowest common multiple. */
fun lcm(vararg numbers: Long): Long = numbers.reduce { a, b -> lcm(a, b) }

/** Returns the [position]th digit from the right, e.g. `123.nthDigitFromRight(2)` returns `2`. */
fun Int.nthDigitFromRight(position: Int): Int? {
    if (position < 1) return null
    if (position > this.toString().length) return null

    val base = (0 until position - 1).fold(1) { acc, _ -> acc * 10 }
    return (this / base) % 10
}

fun ULong.pow(n: Int): ULong = toDouble().pow(n).toULong()
fun Long.sqrt(): Double = kotlin.math.sqrt(toDouble())
