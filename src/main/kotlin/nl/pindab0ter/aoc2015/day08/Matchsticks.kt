package nl.pindab0ter.aoc2015.day08

import nl.pindab0ter.aoc.getInput

private val hexRegex = """(?!<\\)\\x([a-f0-9]{2})""".toRegex()

private const val PLACEHOLDER = "^"

private const val DOUBLE_QUOTE = "\""
private const val ESCAPED_DOUBLE_QUOTE = "\\\""

private const val BACKSLASH = "\\"
private const val ESCAPED_BACKSLASH = "\\\\"


fun unescape(string: String) = string
    .replace(ESCAPED_BACKSLASH, PLACEHOLDER)
    .replace(hexRegex) { matchResult ->
        val hexValue = matchResult.groupValues[1].toInt(16)
        hexValue.toChar().toString()
    }
    .replace(ESCAPED_DOUBLE_QUOTE, DOUBLE_QUOTE)
    .replace(PLACEHOLDER, BACKSLASH)
    .removeSurrounding(DOUBLE_QUOTE)

fun escape(string: String) = string
    .replace(BACKSLASH, ESCAPED_BACKSLASH)
    .replace(DOUBLE_QUOTE, ESCAPED_DOUBLE_QUOTE)
    .let { "\"$it\"" }

fun main() {
    val lines = getInput(2015, 8).lines()

    val rawLength = lines.sumOf(String::length)
    val unescapedLength = lines.map(::unescape).sumOf(String::length)
    val escapedLength = lines.map(::escape).sumOf(String::length)

    println("Difference between character count pre and post unescape: ${rawLength - unescapedLength}")
    println("Difference between character count pre and post escape: ${escapedLength - rawLength}")
}
