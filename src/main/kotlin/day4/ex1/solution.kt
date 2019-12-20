package day4.ex1

fun main() {
    val input = "254032-789860".split("-")

    val start = input[0].toInt()
    val end = input[1].toInt()

    var answer = 0

    for (password in start..end) {
        val digits = password.toString()
        val isFirstCheck = digits.substring(0, 5).mapIndexed { index, digit -> digit == digits[index + 1] }.any { it }
        val isSecondCheck = digits.substring(0, 5).mapIndexed { index, digit -> digit <= digits[index + 1] }.all { it }

        if (isFirstCheck && isSecondCheck) answer += 1
    }

    println(answer)
}
