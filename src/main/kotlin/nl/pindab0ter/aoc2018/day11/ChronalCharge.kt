package nl.pindab0ter.aoc2018.day11

import nl.pindab0ter.aoc.getInput
import nl.pindab0ter.lib.collections.mapAsync
import nl.pindab0ter.lib.nthDigitFromRight

fun main() {
    val input = getInput(2018, 11).toInt()
    val fuelCellGrid = FuelCellGrid(input)

    val square1 = fuelCellGrid.findMostPowerfulSquare(3)
    println("The most powerful square is at ${square1.x},${square1.y} with a power level of ${square1.powerLevel}")

    val square2 = fuelCellGrid.findMostPowerfulSquareOfAnySize()
    println("The most powerful square of any size is at ${square2.x},${square2.y},${square2.size} with a power level of ${square2.powerLevel}")
}

data class Square(val x: Int, val y: Int, val size: Int, val powerLevel: Int)

data class FuelCellGrid(val width: Int, val height: Int, val cells: List<List<Int>>) {

    /**
     * Gets the power level of a 3x3 grid starting at [startX] and [startY]
     *
     * @param startX The x coordinate of the top left corner of the grid.
     * @param startY The y coordinate of the top left corner of the grid.
     * @return The power level of the grid.
     */
    fun powerLevelFor(startX: Int, startY: Int, size: Int): Int {
        return (startY until startY + size).flatMap { y ->
            (startX until startX + size).map { x ->
                cells[x][y]
            }
        }.sum()
    }

    /**
     * Uses [Summed-area table](https://en.wikipedia.org/wiki/Summed-area_table).
     *
     * @param size The size of the square to find.
     * @return The most powerful square of [size]x[size] in the grid of [cells].
     */
    fun findMostPowerfulSquare(size: Int): Square {
        val summedAreaTable = Array(width + 1) { IntArray(height + 1) }

        summedAreaTable.forEachIndexed { i, row ->
            row.forEachIndexed { j, _ ->
                if (i > 0 && j > 0) summedAreaTable[i][j] = cells[i - 1][j - 1] +
                        summedAreaTable[i - 1][j] +
                        summedAreaTable[i][j - 1] -
                        summedAreaTable[i - 1][j - 1]
            }
        }

        return (size..width).flatMap { i ->
            (size..height).map { j ->
                val power = summedAreaTable[i][j] -
                        summedAreaTable[i - size][j] -
                        summedAreaTable[i][j - size] +
                        summedAreaTable[i - size][j - size]

                Square(i - size, j - size, size, power)
            }
        }.maxBy { it.powerLevel }
    }

    // Using mapAsync only saves about 20% on what is otherwise already a fast operation, but I made it, so I'll use it.
    fun findMostPowerfulSquareOfAnySize(): Square = (3..300)
        .mapAsync(::findMostPowerfulSquare)
        .maxBy(Square::powerLevel)

    companion object {
        operator fun invoke(serialNumber: Int, width: Int = 300, height: Int = 300): FuelCellGrid {
            val cells = List(width) { x ->
                List(height) { y ->
                    val rackId = x + 10
                    ((((rackId * y) + serialNumber) * rackId).nthDigitFromRight(3) ?: 0) - 5
                }
            }
            return FuelCellGrid(width, height, cells)
        }
    }
}
