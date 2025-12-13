package nl.pindab0ter.lib

/**
 * Measures and prints the execution time of the given [block].
 *
 * Executes the provided lambda function and measures its execution time in milliseconds.
 * The execution time is printed to standard output with an optional [name] prefix.
 *
 * Examples:
 * ```
 * val result = timing("calculation") {
 *     expensiveOperation()
 * }
 * // Prints: "calculation took 1234 ms."
 *
 * val result = timing {
 *     quickOperation()
 * }
 * // Prints: "Took 42 ms."
 * ```
 *
 * @param name Optional name to prefix the timing output. If `null`, uses "Took" as the prefix.
 * @param block The function to execute and measure.
 * @return The result of executing [block].
 *
 * @see System.currentTimeMillis
 */
fun <T> timing(name: String? = null, block: () -> T): T {
    val start = System.currentTimeMillis()
    try {
        return block.invoke()
    } finally {
        val end = System.currentTimeMillis()

        println(buildString {
            when (name) {
                null -> append("Took ")
                else -> append("$name took ")
            }
            append(end - start)
            append(" ms.")
        })
    }
}

/**
 * Prints the given [messages] separated by `", "`, and a line separator to the standard output stream.
 *
 * @param messages The values to print.
 *
 * @see kotlin.io.println
 */
fun println(vararg messages: Any?) = kotlin.io.println(messages.joinToString(", "))

/**
 * Prints this value to the standard output stream and returns it.
 *
 * Useful for debugging in method chains without breaking the chain.
 *
 * Example:
 * ```
 * listOf(1, 2, 3)
 *     .map { it * 2 }
 *     .println()        // Prints: [2, 4, 6]
 *     .filter { it > 3 }
 *     .println()        // Prints: [4, 6]
 * ```
 *
 * @return This value unchanged.
 *
 * @see kotlin.io.println
 * @see also
 */
fun <T> T.println(): T = also(::println)
