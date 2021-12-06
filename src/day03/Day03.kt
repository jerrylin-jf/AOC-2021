fun main() {

    fun part1(input: List<String>): Int {
        val n = input.first().length

        val gammaCharArray = CharArray(n) { '0' }
        val epsilonCharArray = CharArray(n) { '0' }

        for (i in 0 until n) {
            if (findMostBit(input, i) == '1') {
                gammaCharArray[i] = '1'
            } else {
                epsilonCharArray[i] = '1'
            }
        }

        val gammaRate = gammaCharArray.concatToString().toInt(2)
        val epsilonRate = epsilonCharArray.concatToString().toInt(2)
        return gammaRate * epsilonRate
    }

    fun part2(input: List<String>): Int {
        val n = input.first().length

        var gammaStrings = input
        var i = 0
        while (i < n && gammaStrings.size > 1) {
            val c = findMostBit(gammaStrings, i)
            gammaStrings = gammaStrings.filter { it[i] == c }
            i++
        }

        i = 0
        var epsilonStrings = input
        while (i < n && epsilonStrings.size > 1) {
            val c = findMostBit(epsilonStrings, i)
            epsilonStrings = epsilonStrings.filter { it[i] != c }
            i++
        }

        val gammaRate = gammaStrings.first().toInt(2)
        val epsilonRate = epsilonStrings.first().toInt(2)
        return gammaRate * epsilonRate
    }

    val testInput = readInput("day03/Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("day03/Day03")
    println(part1(input))
    println(part2(input))
}

fun findMostBit(input: List<String>, position: Int): Char {
    var zer0Count = 0
    var oneCount = 0

    input.forEach {
        if (it[position] == '0') {
            zer0Count += 1
        } else {
            oneCount += 1
        }
    }

    return if (zer0Count > oneCount) '0' else '1'
}