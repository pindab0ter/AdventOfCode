package nl.pindab0ter.aoc

import fuel.httpGet
import kotlinx.coroutines.runBlocking
import kotlinx.io.readByteArray
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths


/**
 * @param year The year of the puzzle.
 * @param day The day of the puzzle.
 *
 * @return The input for the given year and day.
 */
fun getInput(year: Int, day: Int): String {
    val paddedDay = day.toString().padStart(2, '0')
    val inputFile = Paths.get("src/main/resources/${year}/day$paddedDay/input").toFile()

    if (!inputFile.exists()) runBlocking {
        if (!inputFile.parentFile.exists()) Files.createDirectories(inputFile.parentFile.toPath())

        val input = downloadPuzzleInput(year, day)

        inputFile.writeBytes(input)
    }

    return inputFile.readText().trimEnd()
}

private suspend fun downloadPuzzleInput(year: Int, day: Int): ByteArray {
    val sessionCookie = File(".session-cookie").readText().trim()

    val response = "https://adventofcode.com/${year}/day/${day}/input".httpGet(
        headers = mapOf(
            // https://www.reddit.com/r/adventofcode/comments/z9dhtd/please_include_your_contact_info_in_the_useragent/
            "User-Agent" to "https://github.com/pindab0ter/AdventOfCode",
            "From" to "hansvanluttikhuizen@me.com",
            "Cookie" to "session=$sessionCookie",
        )
    )

    if (response.statusCode == 400) throw Exception("Invalid session cookie. Please provide a valid session cookie in .session-cookie")
    if (response.statusCode != 200) throw Exception("Unexpected status code: ${response.statusCode}\n${response.source}")

    return response.source.readByteArray()
}
