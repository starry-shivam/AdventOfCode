package day2

import utils.DayInterface
import java.io.File

fun main() {
    val file = File("src/main/kotlin/day2/input_sample.txt")
    val day2 = Day2(file.readLines())
    println("Part 1: ${day2.calculatePart1()}")
    println("Part 1: ${day2.calculatePart2()}")
}

class Day2(private val input: List<String>) : DayInterface {

    data class CubeBox(val red: Int = 0, val green: Int = 0, val blue: Int = 0) {
        // Used for part tow only.
        val power: Int
            get() = red * green * blue

        operator fun plus(other: CubeBox): CubeBox {
            return CubeBox(red + other.red, green + other.green, blue + other.blue)
        }

        operator fun compareTo(other: CubeBox): Int {
            return if (red <= other.red && green <= other.green && blue <= other.blue) 0 else 1
        }
    }

    data class Game(val id: Int, val cubes: List<CubeBox>)

    override fun calculatePart1(): Int {
        val games = input.map { parseGame(it) }
        val maxCubes = CubeBox(red = 12, green = 13, blue = 14)
        val possibleGames = games.filter { it.cubes.all { cube -> cube <= maxCubes } }
        return possibleGames.sumOf { it.id }
    }

    override fun calculatePart2(): Int {
        val games = input.map { parseGame(it) }
        return games.sumOf {
            val resultBox = CubeBox(
                it.cubes.maxOf { cb -> cb.red },
                it.cubes.maxOf { cb -> cb.green },
                it.cubes.maxOf { cb -> cb.blue }
            )
            resultBox.power
        }
    }

    private fun parseColor(s: String): CubeBox {
        val (count, color) = s.split(" ")
        return when (color) {
            "red" -> CubeBox(red = count.toInt())
            "green" -> CubeBox(green = count.toInt())
            "blue" -> CubeBox(blue = count.toInt())
            else -> CubeBox()
        }
    }

    private fun parseCubes(s: String): CubeBox {
        // Accumulate cubes of different color into one
        return s.split(", ").map { parseColor(it) }.reduce { acc, cb -> acc + cb }
    }

    private fun parseGame(s: String): Game {
        val (gameStr, cubesStr) = s.split(": ")
        val gameId = gameStr.split(" ").last().toInt()
        val cubes = cubesStr.split("; ").map { parseCubes(it) }
        return Game(gameId, cubes)
    }
}


