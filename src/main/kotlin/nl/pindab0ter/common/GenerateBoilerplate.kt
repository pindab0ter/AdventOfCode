package nl.pindab0ter.common

import com.github.ajalt.mordant.terminal.ConversionResult.Invalid
import com.github.ajalt.mordant.terminal.ConversionResult.Valid
import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.terminal.info
import com.github.ajalt.mordant.terminal.prompt
import com.github.ajalt.mordant.terminal.success
import java.nio.file.Files
import java.nio.file.Files.createDirectories
import java.nio.file.Paths
import java.time.LocalDate
import java.time.Month.DECEMBER

private data class Input(
    val year: Int,
    val day: Int,
    val title: String,
)

private fun Terminal.getInput(): Input {
    val validYears = LocalDate.now().let { now ->
        when (now.month) {
            DECEMBER -> 2015..now.year
            else -> 2015 until now.year
        }
    }

    val validDays = LocalDate.now().let { now ->
        when (now.month) {
            DECEMBER -> 1..now.dayOfMonth
            else -> 1..25
        }
    }

    val year = prompt(
        prompt = "Year",
        default = validYears.last,
        choices = validYears.toList(),
        showChoices = false,
        invalidChoiceMessage = "Please enter a year for which there is an Advent of Code puzzle:\n",
    ) { input ->
        val year = input.trim().toIntOrNull() ?: return@prompt Invalid("Please enter a valid year")

        Valid(year)
    }!!

    val day = prompt(
        prompt = "Day",
        default = validDays.last,
        choices = validDays.toList(),
        showChoices = false,
        invalidChoiceMessage = "Please enter a day for which there is an Advent of Code puzzle:\n",
    ) { input ->
        val day = input.trim().toIntOrNull() ?: return@prompt Invalid("Please enter a valid day")

        Valid(day)
    }!!

    val title = prompt(
        prompt = "Puzzle title",
        showDefault = false,
    ) { Valid(it.trim()) }!!

    return Input(year, day, title)
}

fun main() {
    val terminal = Terminal()

    val (year, day, title) = terminal.getInput()

    val paddedDay = day.padStart(2, '0')

    val mainFileName = title.camelCase() + ".kt"
    val mainFile = Paths.get("src/${"main"}/kotlin/nl/pindab0ter/aoc$year/day$paddedDay", mainFileName).toFile()
    val testFileName = title.camelCase() + "KtTest.kt"
    val testFile = Paths.get("src/${"test"}/kotlin/nl/pindab0ter/aoc$year/day$paddedDay", testFileName).toFile()

    if (mainFile.exists() && testFile.exists()) {
        terminal.info("Both files already exist.")
        return
    }

    val namespace = "nl.pindab0ter.aoc$year.day$paddedDay"

    if (!mainFile.exists()) {
        if (!mainFile.parentFile.exists()) createDirectories(mainFile.parentFile.toPath())

        val stub = Files.readString(Paths.get("src/main/resources/stubs/kotlin/mainFile.stub"))
        mainFile.writeText(stub.format(namespace, year, day))

        terminal.success("Created ${mainFile.path}")
    }

    if (!testFile.exists()) {
        if (!testFile.parentFile.exists()) createDirectories(testFile.parentFile.toPath())

        val stub = Files.readString(Paths.get("src/main/resources/stubs/kotlin/testFile.stub"))
        val className = "${title.camelCase()}KtTest"
        testFile.writeText(stub.format(namespace, year, day, title, className))

        terminal.success("Created ${testFile.path}")
    }
}
