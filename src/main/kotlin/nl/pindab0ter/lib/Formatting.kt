package nl.pindab0ter.lib

/**
 * Converts the [Int] to a [String] and pads it to the specified [length] at the beginning with the specified character
 * or space.
 *
 * @param length the desired string length.
 * @param padChar the character to pad string with, if it has length less than the [length] specified. Space is used by default.
 * @return Returns a string of length at least [length] consisting of `this` string prepended with [padChar] as many times
 * as are necessary to reach that length.
 */
fun Int.padStart(length: Int, padChar: Char = ' ') = toString().padStart(length, padChar)
