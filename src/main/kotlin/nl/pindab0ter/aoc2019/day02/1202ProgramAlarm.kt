package nl.pindab0ter.aoc2019.day02

import com.github.ajalt.mordant.terminal.ConversionResult.Invalid
import com.github.ajalt.mordant.terminal.ConversionResult.Valid
import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.terminal.prompt
import nl.pindab0ter.aoc.getInput

fun main(args: Array<String>) {
    val inputMemory = getInput(2019, 2).split(",").map(String::toInt)
    val expectedOutput = args.getOrElse(0) {
        Terminal().prompt("Expected output") { input ->
            if (input.trim().toIntOrNull() == null) Invalid("Must be an integer")
            else Valid(input.trim())
        }!!
    }.toInt()

    val computer = IntcodeComputer(inputMemory.toMutableList())

    computer.memory[1] = 12
    computer.memory[2] = 2
    computer.run()
    println("The value at position 0 is ${computer.memory[0]}")

    val nounAndVerb = (0..99).fold(null) { acc: Int?, noun: Int ->
        if (acc != null) return@fold acc

        val verb = (0..99).firstOrNull { verb ->
            val computer = IntcodeComputer(inputMemory.toMutableList())
            computer.memory[1] = noun
            computer.memory[2] = verb
            computer.run()
            computer.memory[0] == expectedOutput
        }

        if (verb != null) return@fold 100 * noun + verb
        else return@fold null
    }

    println("The noun and verb that produce the output $expectedOutput are $nounAndVerb")
}

fun String.parse(): Memory = split(",").map(String::toInt).toMutableList()

typealias Value = Int
typealias Address = Int
typealias Memory = MutableList<Value>

class UnknownOpcodeException(opcode: Value) : Exception("Unknown opcode: $opcode")

class IntcodeComputer(val memory: Memory) {
    var pointer = 0

    val leftParameter: Address
        get() = memory[pointer + 1]
    val rightParameter: Address
        get() = memory[pointer + 2]
    val output: Address
        get() = memory[pointer + 3]

    private fun executeInstruction() {
        when (memory[pointer]) {
            ADD -> {
                memory[output] = memory[leftParameter] + memory[rightParameter]
                pointer += 4
            }

            MULTIPLY -> {
                memory[output] = memory[leftParameter] * memory[rightParameter]
                pointer += 4
            }

            else -> throw UnknownOpcodeException(opcode = memory[pointer])
        }
    }

    fun run(): Memory {
        while (memory[pointer] != EXIT) {
            executeInstruction()
        }
        return memory
    }

    override fun toString(): String {
        return "IntcodeComputer(memory=$memory, pointer=$pointer)"
    }

    companion object {
        const val ADD = 1
        const val MULTIPLY = 2
        const val EXIT = 99
    }
}
