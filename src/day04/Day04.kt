package day04

import readInput
import java.util.Arrays
import kotlin.time.Duration.Companion.seconds

fun main() {

    fun mark(board: BingoBoard, number: Int) {
        for (i in board.indices) {
            val row: List<Cell> = board[i]
            for (cell in row) {
                if (cell.value == number) {
                    cell.checked = true
                    return
                }
            }
        }
    }

    fun hasWinner(boards: List<BingoBoard>): Pair<Boolean, Int> {
        boards.forEachIndexed { index, board ->
            // check row
            for (i in board.indices) {
                board.forEach {
                    if (it.all { cell -> cell.checked }) {
                        return Pair(true, index)
                    }
                }
            }

            // check column
            for (j in board.first().indices) {
                for (i in board.indices) {
                    if (board[i][j].checked.not()) break
                    if (i == board.size - 1) {
                        return Pair(true, index)
                    }
                }
            }
        }

        return Pair(false, -1)
    }

    fun calculateScore(board: BingoBoard, number: Int): Int {
        var sumUnmark = 0
        board.forEach {
            it.forEach { cell ->
                if (cell.checked.not()) {
                    sumUnmark += cell.value
                }
            }
        }
        return sumUnmark * number
    }

    fun part1(input: List<String>): Int {
        val numbers = input.first().getBingoNumber()
        val allBoard: List<BingoBoard> = input.asSequence()
            .filter { it.isNotEmpty() }
            .drop(1)
            .chunked(5)
            .map { it.toBingoBoard() }
            .toList()

        for (number in numbers) {
            allBoard.forEach { mark(it, number) }
            val result = hasWinner(allBoard)
            if (result.first) {
                return calculateScore(allBoard[result.second], number)
            }
        }

        return 0
    }

    fun hasWinnerOnlyColumn(boards: List<BingoBoard>): List<Pair<Boolean, Int>> {
        val all = mutableListOf<Pair<Boolean, Int>>()
        boards.forEachIndexed { index, board ->

            // check row
            for (i in board.indices) {
                board.forEach {
                    if (it.all { cell -> cell.checked }) {
                        all.add(Pair(true, index))
                    }
                }
            }

            // check column
            for (j in board.first().indices) {
                for (i in board.indices) {
                    if (board[i][j].checked.not()) break
                    if (i == board.size - 1) {
                        all.add(Pair(true, index))
                    }
                }
            }
        }

        return all
    }

    fun part2(input: List<String>): Int {
        val numbers = input.first().getBingoNumber()
        val allBoard: List<BingoBoard> = input.asSequence()
            .filter { it.isNotEmpty() }
            .drop(1)
            .chunked(5)
            .map { it.toBingoBoard() }
            .toList()

        val allBoardIndex = allBoard.indices.toMutableList()

        for (number in numbers) {
            allBoard.forEach { mark(it, number) }
            val results = hasWinnerOnlyColumn(allBoard)
            if (results.isNotEmpty()) {
                for (r in results) {
                    allBoardIndex.remove(r.second)

                    if (allBoardIndex.isEmpty()) {
                        return calculateScore(allBoard[r.second], number)
                    }
                }

            }
        }

        return 0
    }

    val testInput = readInput("day04/Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("day04/Day04")
    println(part1(input))
    println(part2(input))
}

typealias BingoBoard = List<List<Cell>>

data class Cell(
    val value: Int,
    var checked: Boolean = false
)

fun String.getBingoNumber(): List<Int> {
    return this.split(",").map { it.toInt() }
}

fun List<String>.toBingoBoard(): BingoBoard {
    return this.map { it.toRow() }.toList()
}

fun String.toRow(): List<Cell> {
    return this.split(" ")
        .asSequence()
        .filter { it.isNotEmpty() }
        .map { Cell(value = it.toInt()) }
        .toList()
}