package day1

import java.io.File

fun main() {
    val file = File("src/main/kotlin/day1/input_sample.txt")
    val lines = file.readText().lines()
    println("Part 1: ${calculatePart1(lines)}")
    println("Part 2: ${calculatePart2(lines)}")
}

private fun calculatePart1(lines: List<String>): Int {
    var sum = 0
    // Loop over every line
    for (line in lines) {
        // Find first and last digits for every line.
        val firstDigit = line.firstOrNull { it.isDigit() }
        val lastDigit = line.lastOrNull { it.isDigit() }
        // convert them into integer and add them in total sum.
        if (firstDigit != null && lastDigit != null) {
            val calibrationValue = "$firstDigit$lastDigit".toInt()
            sum += calibrationValue
        }
    }

    return sum
}

private fun calculatePart2(lines: List<String>): Int {
    var sum = 0
    for(line in lines) {
        val numbers = findNumbers(line)
        sum += "${numbers.first()}${numbers.last()}".toInt()
    }
    return sum
}

private fun findNumbers(line: String): List<Int> {
    val digitMap = mapOf(
        1 to "one",
        2 to "two",
        3 to "three",
        4 to "four",
        5 to "five",
        6 to "six",
        7 to "seven",
        8 to "eight",
        9 to "nine"
    )

    val result = mutableListOf<Int>()

    for (idx in line.indices) {
        // Cut the string to start from current index.
        val tail = line.substring(idx)
        // check if first character is an actual digit (not word).
        val firstChar = tail.firstOrNull()
        if (firstChar?.isDigit() == true) {
            // if it's digit add it in resulting list.
            result.add(Character.getNumericValue(firstChar))
        } else {
            /**
             * If it's not, then loop over each entry from the digitMap
             * and find the one from which tail (the current cut string)
             * starts from & add it to the results.
             */
            digitMap.entries.find { (_, digitString) -> tail.startsWith(digitString) }?.let {
                result.add(it.key)
            }
        }
    }

    return result
}
