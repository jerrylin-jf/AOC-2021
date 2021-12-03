fun main() {

    fun part1(input: List<String>): Int {
        val position = Position(0, 0)

        input.map { it.toCommand() }
            .forEach {
                when (it.direction) {
                    Direction.FORWARD -> position.horizontal += it.unit
                    Direction.DOWN -> position.depth += it.unit
                    Direction.UP -> position.depth -= it.unit
                }
            }

        return position.horizontal * position.depth
    }

    fun part2(input: List<String>): Int {
        val position = Position(0, 0)
        var aim = 0

        input.map { it.toCommand() }
            .forEach {
                when (it.direction) {
                    Direction.UP -> aim -= it.unit
                    Direction.DOWN -> aim += it.unit
                    Direction.FORWARD -> {
                        position.horizontal += it.unit
                        position.depth += aim * it.unit
                    }
                }
            }

        return position.horizontal * position.depth
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

private class Position(
    var horizontal: Int,
    var depth: Int
)

private data class Command(
    val direction: Direction,
    val unit: Int
)

private enum class Direction {
    FORWARD, DOWN, UP,
}

private fun String.toCommand(): Command {
    val strings = this.split(" ")

    return Command(Direction.valueOf(strings[0].uppercase()), strings[1].toInt())
}