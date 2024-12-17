package day17

import utils.IO
import kotlin.math.pow

enum class RegistryKey {
    A, B, C
}

enum class Operation(val value: Int) {
    Adv(0), Bxl(1), Bst(2), Jnz(3), Bxc(4), Out(5), Bdv(6), Cdv(7);

    companion object {
        fun getByValue(value: Int) = Operation.entries.first { it.value == value }
    }
}

enum class OperandType {
    Literal, Combo
}

abstract class AbstractSimulator {
    protected var programs = listOf<Int>()
    protected var registry = mutableMapOf<RegistryKey, Int>()
    protected val output = mutableListOf<Int>()
    protected var pointer = 0

    init {
        val (registries, program) = IO.readFileLines("day17/smallInput.txt").joinToString("\n").split("\n\n")

        programs = "(\\d)".toRegex().findAll(program).toList().map { it.value.toInt() }

        registry = registries.split("\n").mapNotNull { registry ->
            val match = ".*\\s([A-C]):\\s(\\d+)".toRegex().find(registry)?.destructured

            if (match == null) return@mapNotNull null

            val (key, value) = match
            val registryKey = when (key) {
                "A" -> RegistryKey.A
                "B" -> RegistryKey.B
                "C" -> RegistryKey.C
                else -> throw IllegalArgumentException("Incorrect registry key value")
            }

            return@mapNotNull registryKey to value.toInt()
        }.toMap().toMutableMap()
    }

    protected fun getOperandValue(operand: Int, type: OperandType) = when (type) {
        OperandType.Literal -> operand
        OperandType.Combo -> when (operand) {
            in 0..3 -> operand
            4 -> registry.getValue(RegistryKey.A)
            5 -> registry.getValue(RegistryKey.B)
            6 -> registry.getValue(RegistryKey.C)
            else -> throw IllegalArgumentException("Incorrect combo operand value")
        }
    }

    protected fun divideRegistryA(operand: Int): Int {
        val numerator = registry.getValue(RegistryKey.A)
        val comboOperand = getOperandValue(operand, OperandType.Combo)
        val denominator = 2.0.pow(comboOperand).toInt()

        return numerator / denominator
    }

    protected fun handleAdv(operand: Int) {
        registry[RegistryKey.A] = divideRegistryA(operand)
    }

    protected fun handleBxl(operand: Int) {
        val literalOperand = getOperandValue(operand, OperandType.Literal)

        registry[RegistryKey.B] = registry.getValue(RegistryKey.B) xor literalOperand
    }

    protected fun handleBst(operand: Int) {
        val comboOperand = getOperandValue(operand, OperandType.Combo)

        registry[RegistryKey.B] = comboOperand % 8
    }

    protected fun handleJnz(operand: Int) {
        val registryA = registry.getValue(RegistryKey.A)

        if (registryA == 0) return

        pointer = getOperandValue(operand, OperandType.Literal)
    }

    protected fun handleBxc(operand: Int) {
        registry[RegistryKey.B] = registry.getValue(RegistryKey.B) xor registry.getValue(RegistryKey.C)
    }

    protected fun handleOut(operand: Int) {
        val comboOperand = getOperandValue(operand, OperandType.Combo)

        output.add(comboOperand % 8)
    }

    protected fun handleBdv(operand: Int) {
        registry[RegistryKey.B] = divideRegistryA(operand)
    }

    protected fun handleCdv(operand: Int) {
        registry[RegistryKey.C] = divideRegistryA(operand)
    }

    abstract fun solve()
}

class SimpleSimulator : AbstractSimulator() {
    override fun solve() {
        while (pointer < programs.size - 1) {
            val (opcode, operand) = programs.slice(pointer..<pointer + 2)
            val operation = Operation.getByValue(opcode)

            pointer += 2

            when (operation) {
                Operation.Adv -> handleAdv(operand)
                Operation.Bxl -> handleBxl(operand)
                Operation.Bst -> handleBst(operand)
                Operation.Jnz -> handleJnz(operand)
                Operation.Bxc -> handleBxc(operand)
                Operation.Out -> handleOut(operand)
                Operation.Bdv -> handleBdv(operand)
                Operation.Cdv -> handleCdv(operand)
            }
        }

        println("Day 17, part 1: ${output.joinToString(",")}")
    }
}

class CorruptedRegistySimulator : AbstractSimulator() {
    override fun solve() {
        TODO("Not yet implemented")
    }
}

fun main() {
    val simpleSimulator = SimpleSimulator()
    simpleSimulator.solve()

    val corruptedRegistySimulator = CorruptedRegistySimulator()
    corruptedRegistySimulator.solve()
}
