package nl.pindab0ter.lib

import java.util.*

fun String.uppercaseFirst(): String = replaceFirstChar { it.titlecase(Locale.getDefault()) }

/** Converts to camelCase by removing non-alphabetic characters. */
fun String.camelCase() = split(Regex("""[^A-Za-z]"""))
    .joinToString("", transform = String::uppercaseFirst)
