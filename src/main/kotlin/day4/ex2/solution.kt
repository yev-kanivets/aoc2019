package day4.ex2

fun main() {
    val input = "254032-789860".split("-")

    val start = input[0].toInt()
    val end = input[1].toInt()

    var answer = 0

    for (password in start..end) {
        val digits = password.toString()
        val checks = listOf(
            listOf(false),
            digits.substring(0, 5).mapIndexed { index, digit -> digit == digits[index + 1] },
            listOf(false)
        ).flatten()

        val isFirstCheck = checks.subList(1, 6).mapIndexed { index, check -> !checks[index] && check && !checks[index + 2] }.any { it }
        val isSecondCheck = digits.substring(0, 5).mapIndexed { index, digit -> digit <= digits[index + 1] }.all { it }

        if (isFirstCheck && isSecondCheck) answer += 1
    }

    println(answer)
}
