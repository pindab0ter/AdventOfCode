package nl.pindab0ter.lib

import java.util.*

/**
 * Returns a copy of this string having its first character uppercased, or the original string if it’s empty.
 */
fun String.uppercaseFirst(): String = replaceFirstChar { it.titlecase(Locale.getDefault()) }

/**
 * Converts this string to camel case.
 */
fun String.camelCase() = split(Regex("""[^A-Za-z]"""))
    .joinToString("", transform = String::uppercaseFirst)
