package day17

import utils.IO
import kotlin.math.pow

abstract class AbstractSimulator() {
    protected var rawProgram = ""
    protected var program = listOf<Int>()
    protected var registry = mutableMapOf<RegistryKey, ULong>()
    protected var pointer = 0
    protected val output = mutableListOf<ULong>()

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

    init {
        val (inputRegistries, inputProgram) = IO.readFileLines("day17/smallInput.txt").joinToString("\n").split("\n\n")

        rawProgram = inputProgram
        program = "(\\d)".toRegex().findAll(inputProgram).toList().map { it.value.toInt() }

        registry = inputRegistries.split("\n").mapNotNull { registry ->
            val match = ".*\\s([A-C]):\\s(\\d+)".toRegex().find(registry)?.destructured

            if (match == null) return@mapNotNull null

            val (key, value) = match
            val registryKey = when (key) {
                "A" -> RegistryKey.A
                "B" -> RegistryKey.B
                "C" -> RegistryKey.C
                else -> throw IllegalArgumentException("Incorrect registry key value")
            }

            return@mapNotNull registryKey to value.toULong()
        }.toMap().toMutableMap()
    }

    protected fun getOperandValue(operand: Int, type: OperandType) = when (type) {
        OperandType.Literal -> operand.toULong()
        OperandType.Combo -> when (operand) {
            in 0..3 -> operand.toULong()
            4 -> registry.getValue(RegistryKey.A)
            5 -> registry.getValue(RegistryKey.B)
            6 -> registry.getValue(RegistryKey.C)
            else -> throw IllegalArgumentException("Incorrect combo operand value")
        }
    }

    protected fun divideRegistryA(operand: Int): ULong {
        val numerator = registry.getValue(RegistryKey.A)
        val comboOperand = getOperandValue(operand, OperandType.Combo)
        val denominator = 2.0.pow(comboOperand.toDouble()).toULong()

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

        registry[RegistryKey.B] = comboOperand % 8UL
    }

    protected fun handleJnz(operand: Int) {
        val registryA = registry.getValue(RegistryKey.A)

        if (registryA == 0UL) return

        pointer = getOperandValue(operand, OperandType.Literal).toInt()
    }

    protected fun handleBxc(operand: Int) {
        registry[RegistryKey.B] = registry.getValue(RegistryKey.B) xor registry.getValue(RegistryKey.C)
    }

    protected fun handleOut(operand: Int) {
        val comboOperand = getOperandValue(operand, OperandType.Combo)

        output.add(comboOperand % 8UL)
    }

    protected fun handleBdv(operand: Int) {
        registry[RegistryKey.B] = divideRegistryA(operand)
    }

    protected fun handleCdv(operand: Int) {
        registry[RegistryKey.C] = divideRegistryA(operand)
    }

    fun overrideRegistryA(value: ULong) {
        registry[RegistryKey.A] = value
    }

    abstract fun solve(): String
}

class SimpleSimulator : AbstractSimulator() {
    override fun solve(): String {
        while (pointer < program.size - 1) {
            val (opcode, operand) = program.slice(pointer..<pointer + 2)
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

        return output.joinToString(",")
    }
}

class CorruptedRegistrySimulator : AbstractSimulator() {
    override fun solve(): String {
        var correctSuffixes = listOf(0UL)

        for (suffixLength in 1..program.size) {
            val newSuffixes = mutableListOf<ULong>()

            for (suffix in correctSuffixes) {
                for (offset in 0UL..<8UL) {
                    val partialRegistryA = 8UL * suffix + offset
                    val simulator = SimpleSimulator()

                    simulator.overrideRegistryA(partialRegistryA)
                    val output = simulator.solve()

                    val programSuffix = program.slice(program.size - suffixLength..<program.size).joinToString(",")

                    if (programSuffix == output) {
                        newSuffixes.add(partialRegistryA)
                    }
                }
            }

            correctSuffixes = newSuffixes.toList()
        }

        return correctSuffixes.min().toString()
    }
}

fun main() {
    val simpleSimulator = SimpleSimulator()
    val simpleOutput = simpleSimulator.solve()

    println("Day 17, part 1: $simpleOutput")

    val corruptedRegistrySimulator = CorruptedRegistrySimulator()
    val fixedRegistryA = corruptedRegistrySimulator.solve()

    println("Day 17, part 2: $fixedRegistryA")
}
