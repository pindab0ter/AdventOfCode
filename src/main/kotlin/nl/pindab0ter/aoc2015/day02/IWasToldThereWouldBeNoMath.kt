package nl.pindab0ter.aoc2015.day02

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.lib.println

fun main() {
    val input = getInput(2015, 2)

    val boxes = parse(input)
    val requiredWrappingPaper = boxes.sumOf { box -> box.requiredWrappingPaper }

    println("Required wrapping paper for all boxes: $requiredWrappingPaper")
}

fun parse(input: String): List<Box> = input.lines().map { line ->
    val (length, width, height) = line.split("x").map { numberString: String -> numberString.toInt() }
    Box(length, width, height)
}

data class Box(val length: Int, val width: Int, val height: Int) {
    val sides = listOf(width * length, width * height, length * height)
    val smallestSide = sides.min()
    val surface = sides.sumOf { it * 2 }
    val requiredWrappingPaper = surface + smallestSide
}
