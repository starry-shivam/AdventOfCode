package day4

import utils.DayInterface
import java.io.File

fun main() {
    val file = File("src/main/kotlin/day4/input_sample.txt")
    val day4 = Day4(file.readLines())
    println("Part 1: ${day4.calculatePart1()}")
    println("Part 2: ${day4.calculatePart2()}")
}

class Day4(private val input: List<String>) : DayInterface {

    data class Card(
        val id: Int,
        val scratchedNums: List<Int>,
        val winningNums: List<Int>
    )

    override fun calculatePart1(): Int {
        val cards = parseCards(input)
        var sum = 0
        cards.forEach { c ->
            var point = 0
            c.scratchedNums.forEach { if (it in c.winningNums) point = if (point == 0) 1 else point * 2 }
            sum += point
        }
        return sum
    }

    override fun calculatePart2(): Int {
        val instances = MutableList(input.size) { 1 }
        val cards = parseCards(input)
        cards.forEachIndexed { i, c ->
            val match = c.scratchedNums.count { it in c.winningNums }
            for (j in i + 1..i + match)
                instances[j] += instances[i]
        }
        return instances.sum()
    }

    private fun parseCards(input: List<String>): List<Card> {
        return input.map { line ->
            val parts = line.split(":")
            val cardId = parts[0].trim().removePrefix("Card ").trim().toInt()
            val numbers = parts[1].split("|").map { n ->
                n.trim().split(" ").filter { it.isNotBlank() }.map { it.toInt() }
            }
            Card(cardId, numbers[0], numbers[1])
        }
    }
}