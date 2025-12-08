package nl.pindab0ter.aoc2015.day07

import arrow.core.Either
import arrow.core.identity
import arrow.core.left
import arrow.core.right
import nl.pindab0ter.aoc.getInput


data class Instruction(
    val a: Either<UShort, String>?,
    val operator: Operator?,
    val b: Either<UShort, String>?,
)

private val regex = Regex("^([a-z0-9]+)? ?([A-Z]+)? ?([a-z0-9]+)? -> ([a-z]+)$")

fun parse(input: String): Map<String, Instruction> = input.lines()
    .mapNotNull { regex.matchEntire(it)?.destructured }
    .associate { (a, operator, b, target) ->
        target to Instruction(
            a = a.toUShortOrNull()?.left() ?: if (a.isNotEmpty()) a.right() else null,
            operator = Operator.from(operator),
            b = b.toUShortOrNull()?.left() ?: if (b.isNotEmpty()) b.right() else null,
        )
    }

private val memo = mutableMapOf<String, UShort>()
fun Map<String, Instruction>.getSignal(wire: String): UShort = memo.getOrPut(wire) {
    get(wire)!!.run {
        val a = a?.fold(::identity, ::getSignal)
        val b = b?.fold(::identity, ::getSignal)

        when {
            operator == null -> a!!
            a == null -> b!!.inv()
            else -> operator.invoke(a, b!!)
        }
    }
}

fun main() {
    val circuit = parse(getInput(2015, 7))

    val signal = circuit.getSignal("a")
    println("Wire a is provided with signal: $signal")

    val updatedCircuit = circuit.toMutableMap().apply {
        this["b"] = Instruction(signal.left(), null, null)
    }
    memo.clear()
    println("After updating wire b, wire a is provided with signal: ${updatedCircuit.getSignal("a")}")
}
