package day2.ex2

import PATH_PREFIX
import java.io.File

fun execute(instruction: List<Int>, noun: Int, verb: Int): Int {
    val commands = instruction.toMutableList().apply {
        this[1] = noun
        this[2] = verb
    }

    var index = 0
    loop@ while (true) {
        when (commands[index]) {
            1 -> commands[commands[index + 3]] = commands[commands[index + 1]] + commands[commands[index + 2]]
            2 -> commands[commands[index + 3]] = commands[commands[index + 1]] * commands[commands[index + 2]]
            99 -> break@loop
        }
        index += 4
    }

    return commands[0]
}

fun main() {
    val input = File("$PATH_PREFIX/day2/ex2/input.txt").readText().split(",")
    val instruction = input.map { it.toInt() }

    val target = 19690720

    for (noun in 0..99) {
        for (verb in 0..99) {
            if (target == execute(instruction, noun, verb)) {
                println(100 * noun + verb)
                return
            }
        }
    }
}
