package day6

import utils.DayInterface
import java.io.File

fun main() {
    val file = File("src/main/kotlin/day6/input_sample.txt")
    val day6 = Day6(file.readLines())
    println("Part 1: ${day6.calculatePart1()}")
    println("Part 2: ${day6.calculatePart2()}")
}

class Day6(private val input: List<String>) : DayInterface {

    data class Race(val time: Long, val distance: Long)

    override fun calculatePart1(): Int {
        val races = parseRaces(input)
        return countWaysToWin(races)
    }

    override fun calculatePart2(): Int {
        val race = parseRaces(input, combined = true)
        return countWaysToWin(race)
    }

    private fun parseRaces(input: List<String>, combined: Boolean = false): List<Race> {
        return if (combined) {
            val time = input[0].filter { it.isDigit() }.toLong()
            val distance = input[1].filter { it.isDigit() }.toLong()
            listOf(Race(time, distance))
        } else {
            val times = input[0].substringAfter("Time: ").split(" ").mapNotNull { it.toLongOrNull() }
            val distances = input[1].substringAfter("Distance: ").split(" ").mapNotNull { it.toLongOrNull() }
            times.zip(distances) { t, d -> Race(t, d) }
        }
    }

    private fun countWaysToWin(races: List<Race>): Int {
        return races
            .map { race ->
                (1..<race.time)
                    .count { held ->
                        // Current speed = held
                        val remainingTime = race.time - held
                        (remainingTime * held) > race.distance
                    }
            }
            .reduce { acc, n -> acc * n }
    }
}