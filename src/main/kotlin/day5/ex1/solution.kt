package day5.ex1

import PATH_PREFIX
import java.io.File
import java.lang.IllegalArgumentException

fun main() {
    val input = File("$PATH_PREFIX/day5/ex1/input.txt").readText().split(",")
    val commands = input.map { it.toInt() }.toMutableList()

    var index = 0
    loop@ while (true) {
        when (commands[index] % 10) {
            1 -> plus(commands, index).also { index += 4 }
            2 -> multiply(commands, index).also { index += 4 }
            3 -> readLn(commands, index).also { index += 2 }
            4 -> writeLn(commands, index).also { index += 2 }
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
