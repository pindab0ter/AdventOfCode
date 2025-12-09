package nl.pindab0ter.lib

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
 */
fun println(vararg messages: Any?) = kotlin.io.println(messages.joinToString(", "))

fun <T> T.println(): T = also(::println)
