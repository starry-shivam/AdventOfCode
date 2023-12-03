package day3

import java.io.File

fun main() {
    val file = File("src/main/kotlin/day3/input_sample.txt")
    val lines = file.readLines()
    println("Part 1: ${calculatePart1(lines)}")
    println("Part 2: ${calculatePart2(lines)}")

}

fun getAdjacentSymbol(input: List<String>, rowIndex: Int, range: IntRange): Pair<Int, Int>? {
    val symbols = "@#$%&*/=+-"
    // Check adjacent rows
    val adjacentRows = listOf(rowIndex - 1, rowIndex + 1)
    for (adjacentRow in adjacentRows) {
        for (colIndex in maxOf(range.first - 1, 0)..minOf(range.last + 1, input[rowIndex].lastIndex)) {
            if (adjacentRow in input.indices && input[adjacentRow][colIndex] in symbols) {
                return adjacentRow to colIndex
            }
        }
    }

    // Check adjacent columns
    val adjacentColumns = listOf(range.first - 1, range.last + 1)
    for (adjacentColumn in adjacentColumns) {
        if (adjacentColumn in input[rowIndex].indices && input[rowIndex][adjacentColumn] in symbols) {
            return rowIndex to adjacentColumn
        }
    }

    // No adjacent symbol found
    return null
}


fun calculatePart1(input: List<String>): Int {
    // Extract numbers with their range mapped to row index
    val numbersWithRanges = input.flatMapIndexed { rowIndex, line ->
        Regex("\\d+").findAll(line).map { rowIndex to it.range }
    }

    // Calculate the sum of selected numbers based on adjacency conditions
    return numbersWithRanges.sumOf { (rowIndex, range) ->
        if (getAdjacentSymbol(input, rowIndex, range) != null) {
            input[rowIndex].substring(range).toInt()
        } else {
            0
        }
    }
}

fun calculatePart2(input: List<String>): Int {
    // Extract numbers with their indices and ranges
    val numbersWithRanges = input.flatMapIndexed { rowIndex, line ->
        Regex("\\d+").findAll(line).map { rowIndex to it.range }
    }

    // Calculate the sum based on adjacency conditions and '*' symbols
    var sum = 0
    for ((i, r1) in numbersWithRanges) {
        for ((j, r2) in numbersWithRanges) {
            val (x1, y1) = getAdjacentSymbol(input, i, r1) ?: continue
            val (x2, y2) = getAdjacentSymbol(input, j, r2) ?: continue

            // Check conditions for adjacency and '*' symbol
            if ((i != j || r1 != r2) && x1 == x2 && y1 == y2 && input[x1][y1] == '*') {
                sum += input[i].substring(r1).toInt() * input[j].substring(r2).toInt()
            }
        }
    }

    // Divide sum by 2 as each pair is considered twice
    return sum / 2
}