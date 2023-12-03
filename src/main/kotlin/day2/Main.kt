package day2

import java.io.File

fun main() {
    val file = File("src/main/kotlin/day2/input_sample.txt")
    val games = file.readText().lines().map { parseGame(it) }
    println("Part 1: ${calculatePart1(games)}")
    println("Part 2: ${calculatePart2(games)}")
}

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

fun parseColor(s: String): CubeBox {
    val (count, color) = s.split(" ")
    return when (color) {
        "red" -> CubeBox(red = count.toInt())
        "green" -> CubeBox(green = count.toInt())
        "blue" -> CubeBox(blue = count.toInt())
        else -> CubeBox()
    }
}

fun parseCubes(s: String): CubeBox {
    // Accumulate cubes of different color into one
    return s.split(", ").map { parseColor(it) }.reduce { acc, cb -> acc + cb }
}

fun parseGame(s: String): Game {
    val (gameStr, cubesStr) = s.split(": ")
    val gameId = gameStr.split(" ").last().toInt()
    val cubes = cubesStr.split("; ").map { parseCubes(it) }
    return Game(gameId, cubes)
}

fun calculatePart1(games: List<Game>): Int {
    val maxCubes = CubeBox(red = 12, green = 13, blue = 14)
    val possibleGames = games.filter { it.cubes.all { cube -> cube <= maxCubes } }
    return possibleGames.sumOf { it.id }
}

fun calculatePart2(games: List<Game>): Int {
    return games.sumOf {
        val resultBox = CubeBox(
            it.cubes.maxOf { cb -> cb.red },
            it.cubes.maxOf { cb -> cb.green },
            it.cubes.maxOf { cb -> cb.blue }
        )
        resultBox.power
    }
}