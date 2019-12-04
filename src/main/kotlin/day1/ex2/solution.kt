package day1.ex2

import PATH_PREFIX
import java.io.File

private fun recursiveSum(value: Int): Int {
    val fuelNeeded = value / 3 - 2
    return if (fuelNeeded <= 0) 0 else fuelNeeded + recursiveSum(fuelNeeded)
}

fun main() {
    val input = File("$PATH_PREFIX/day1/ex2/input.txt").readLines()
    val output = input.sumBy { recursiveSum(it.toInt()) }
    print(output)
}
