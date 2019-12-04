package day1.ex1

import PATH_PREFIX
import java.io.File

fun main() {
    val input = File("$PATH_PREFIX/day1/ex1/input.txt").readLines()
    val output = input.sumBy { it.toInt() / 3 - 2 }
    print(output)
}
