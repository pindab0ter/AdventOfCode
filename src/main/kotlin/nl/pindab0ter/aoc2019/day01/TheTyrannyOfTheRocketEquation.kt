package nl.pindab0ter.aoc2019.day01

import nl.pindab0ter.aoc.getInput

fun fuelRequired(weight: Int): Int = (weight / 3 - 2).coerceAtLeast(0)

fun totalFuelRequired(weight: Int): Int {
    tailrec fun addedWeight(addedWeight: Int, total: Int): Int = when {
        addedWeight <= 0 -> total
        else -> addedWeight(fuelRequired(addedWeight), total + addedWeight)
    }

    return addedWeight(fuelRequired(weight), 0)
}

fun main() {
    val modules = getInput(2019, 1).parse()

    val fuelRequiredForModules = modules.sumOf(::fuelRequired)
    println("The total fuel required to launch all modules is $fuelRequiredForModules")

    val fuelRequiredForModulesAndFuel = modules.sumOf(::totalFuelRequired)
    println("The total fuel required to launch all modules and fuel is $fuelRequiredForModulesAndFuel")
}

fun String.parse(): List<Int> = lines().map(String::toInt)
