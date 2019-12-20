package day5.ex2

import PATH_PREFIX
import java.io.File
import java.lang.IllegalArgumentException

fun main() {
    val input = File("$PATH_PREFIX/day5/ex2/input.txt").readText().split(",")
    val commands = input.map { it.toInt() }.toMutableList()

    var index = 0
    loop@ while (true) {
        when (commands[index] % 10) {
            1 -> plus(commands, index).also { index += 4 }
            2 -> multiply(commands, index).also { index += 4 }
            3 -> readLn(commands, index).also { index += 2 }
            4 -> writeLn(commands, index).also { index += 2 }
            5 -> jumpIfTrue(commands, index).also { if (it == 0) index += 3 else index = it }
            6 -> jumpIfFalse(commands, index).also { if (it == 0) index += 3 else index = it }
            7 -> lessThen(commands, index).also { index += 4 }
            8 -> equals(commands, index).also { index += 4 }
            99 -> break@loop
        }
    }
}

private fun plus(commands: MutableList<Int>, index: Int) {
    val args = retrieveArgs(commands, index, 3)
    commands[commands[index + 3]] = args[0] + args[1]
}

private fun multiply(commands: MutableList<Int>, index: Int) {
    val args = retrieveArgs(commands, index, 3)
    commands[commands[index + 3]] = args[0] * args[1]
}

private fun readLn(commands: MutableList<Int>, index: Int) {
    commands[commands[index + 1]] = readLine()!!.toInt()
}

private fun writeLn(commands: MutableList<Int>, index: Int) {
    println(commands[commands[index + 1]])
}

private fun jumpIfTrue(commands: MutableList<Int>, index: Int): Int {
    val args = retrieveArgs(commands, index, 2)
    return if (args[0] == 0) 0 else args[1]
}

private fun jumpIfFalse(commands: MutableList<Int>, index: Int): Int {
    val args = retrieveArgs(commands, index, 2)
    return if (args[0] == 0) args[1] else 0
}

private fun lessThen(commands: MutableList<Int>, index: Int) {
    val args = retrieveArgs(commands, index, 3)
    commands[commands[index + 3]] = if (args[0] < args[1]) 1 else 0
}

private fun equals(commands: MutableList<Int>, index: Int) {
    val args = retrieveArgs(commands, index, 3)
    commands[commands[index + 3]] = if (args[0] == args[1]) 1 else 0
}

private fun retrieveArgs(commands: MutableList<Int>, index: Int, argsCount: Int): List<Int> {
    var command = commands[index]
    val args = mutableListOf<Int>()

    command /= 100
    for (i in 1..argsCount) {
        val argMode = command % 10
        args += if (argMode == 1) commands[index + i] else commands[commands[index + i]]
        command /= 10
    }

    return args
}
