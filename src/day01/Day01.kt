package day01

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.toInt() }
            .windowed(2)
            .count { (a, b) -> a < b }
    }

    fun part2(input: List<String>): Int {
        return input.map { it.toInt() }
            .windowed(3)
            .windowed(2)
            .count { (a, b) -> a.sum() < b.sum() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day01/Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("day01/Day01")
    println(part1(input))
    println(part2(input))
}
