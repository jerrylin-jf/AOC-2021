fun main() {
    fun part1(input: List<String>): Int {
        var count = 0
        for (i in input.indices) {
            if (i == 0) continue
            val cur = input[i].toInt()

            if (input[i - 1].toInt() < cur) {
                count++
            }
        }

        return count
    }

    fun part2(input: List<String>): Int {
        val list = mutableListOf<String>()

        var sum = 0
        for (i in input.indices) {
            val nums = input[i].toInt()
            sum += nums
            if (i <= 1) {
                continue
            }

            val pre = if (i >= 3) input[i - 3].toInt() else 0
            sum -= pre
            list.add(sum.toString())
        }
        return part1(list)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
