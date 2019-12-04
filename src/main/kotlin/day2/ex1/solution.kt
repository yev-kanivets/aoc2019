package day2.ex1

import PATH_PREFIX
import java.io.File

fun main() {
    val input = File("$PATH_PREFIX/day2/ex1/input.txt").readText().split(",")
    val commands = input.map { it.toInt() }.toMutableList().apply {
        this[1] = 12
        this[2] = 2
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

    print(commands[0])
}
