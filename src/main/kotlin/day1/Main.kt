/**
 * Something is wrong with global snow production, and you've been selected to take a look.
 * The Elves have even given you a map; on it, they've used stars to mark the top fifty
 * locations that are likely to be having problems.
 *
 * You've been doing this long enough to know that to restore snow operations, you need to
 * check all fifty stars by December 25th.
 *
 * Collect stars by solving puzzles. Two puzzles will be made available on each day in the
 * Advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle
 * grants one star. Good luck!
 *
 * You try to ask why they can't just use a weather machine ("not powerful enough") and where
 * they're even sending you ("the sky") and why your map looks mostly blank ("you sure ask a
 * lot of questions") and hang on did you just say the sky ("of course, where do you think
 * snow comes from") when you realize that the Elves are already loading you into a trebuchet
 * ("please hold still, we need to strap you in").
 *
 * As they're making the final adjustments, they discover that their calibration document (your
 * puzzle input) has been amended by a very young Elf who was apparently just excited to show off
 * her art skills. Consequently, the Elves are having trouble reading the values on the document.
 *
 * The newly-improved calibration document consists of lines of text; each line originally
 * contained a specific calibration value that the Elves now need to recover. On each line, the
 * calibration value can be found by combining the first digit and the last digit (in that order)
 * to form a single two-digit number.
 *
 * For example:
 *
 * 1abc2
 * pqr3stu8vwx
 * a1b2c3d4e5f
 * treb7uchet
 * In this example, the calibration values of these four lines are 12, 38, 15, and 77. Adding these
 * together produces 142.
 *
 * Consider your entire calibration document. What is the sum of all of the calibration values?
 */

package day1

import java.io.File

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

/**
 * --- Part Two ---
 * Your calculation isn't quite right. It looks like some of the digits are actually spelled
 * out with letters: one, two, three, four, five, six, seven, eight, and nine also count as
 * valid "digits".
 *
 * Equipped with this new information, you now need to find the real first and last digit on
 * each line. For example:
 *
 * two1nine
 * eightwothree
 * abcone2threexyz
 * xtwone3four
 * 4nineeightseven2
 * zoneight234
 * 7pqrstsixteen
 * In this example, the calibration values are 29, 83, 13, 24, 42, 14, and 76. Adding these
 * together produces 281.
 *
 * What is the sum of all of the calibration values?
 */

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

// ======================================== RESULTS =============================================== //

fun main() {
    val file = File("src/main/kotlin/question1/input_sample.txt")
    val lines = file.readText().lines()
    println("Part 1: ${calculatePart1(lines)}")
    println("Part 2: ${calculatePart2(lines)}")
}
