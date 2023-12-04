package utils

/**
 * DayInterface is a base interface for solving Advent of Code 2023 daily questions.
 * The interface defines methods for calculating solutions to two parts of each daily puzzle.
 *
 * Each implementation of this interface should provide logic to solve both parts of the daily puzzle.
 */
interface DayInterface {

    /**
     * Calculates the solution for Part 1 of the Advent of Code 2023 daily puzzle.
     *
     * @return An integer representing the solution to Part 1.
     */
    fun calculatePart1(): Int

    /**
     * Calculates the solution for Part 2 of the Advent of Code 2023 daily puzzle.
     *
     * @return An integer representing the solution to Part 2.
     */
    fun calculatePart2(): Int
}
