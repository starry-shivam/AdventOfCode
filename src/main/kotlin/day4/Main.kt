package day4

import utils.DayInterface
import java.io.File

fun main() {
    val file = File("src/main/kotlin/day4/input_sample.txt")
    val day4 = Day4(file.readLines())
    println(day4.calculatePart1())
    println(day4.calculatePart2())
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
            c.scratchedNums.forEach {
                if (it in c.winningNums) {
                    point = if (point == 0) 1 else point * 2
                }
            }
            sum += point
        }
        return sum
    }

    override fun calculatePart2(): Int {
        TODO()
    }

    private fun parseCards(input: List<String>): List<Card> {
        val cards = input.map { line ->
            val parts = line.split(":")
            val cardId = parts[0].trim().removePrefix("Card ").trim().toInt()
            val numbers = parts[1].split("|").map {
                it.trim().split(" ").filter { it.isNotBlank() }.map { it.toInt() }
            }
            Card(cardId, numbers[0], numbers[1])
        }
        return cards
    }
}