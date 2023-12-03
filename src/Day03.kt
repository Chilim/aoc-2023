const val dotSymbol = '.'

val directions =
    listOf(Pair(-1, -1), Pair(-1, 0), Pair(-1, 1), Pair(0, -1), Pair(0, 1), Pair(1, -1), Pair(1, 0), Pair(1, 1))

fun main() {

    fun isCharSymbol(char: Char) = !char.isDigit() && char != dotSymbol

    fun extractNumberAndNewIndex(line: String, colIndex: Int): Pair<String, Int> {
        var startIndex = colIndex
        var endIndex = colIndex

        while (startIndex > 0 && line[startIndex - 1].isDigit()) {
            startIndex--
        }

        while (endIndex < line.length - 1 && line[endIndex + 1].isDigit()) {
            endIndex++
        }

        val number = line.substring(startIndex..endIndex)

        return Pair(number, endIndex + 1)
    }


    fun isAdjacentToSymbol(input: List<String>, rowIdx: Int, colIdx: Int): Boolean {
        for ((x, y) in directions) {
            val candidate = input.getOrNull(rowIdx + x)?.getOrNull(colIdx + y)
            if (candidate != null && isCharSymbol(candidate)) {
                return true
            }
        }
        return false
    }

    fun sumPrNr(input: List<String>): Int {
        var sum = 0

        var colIdx = 0

        for (rowIdx in input.indices) {
            val rowLen = input[rowIdx].length

            while (colIdx < rowLen) {
                if (input[rowIdx][colIdx].isDigit() && isAdjacentToSymbol(input, rowIdx, colIdx)) {
                    val (number, newColIdx) = extractNumberAndNewIndex(input[rowIdx], colIdx)
                    sum += number.toInt()
                    colIdx = newColIdx
                } else {
                    colIdx += 1
                }
            }
            colIdx = 0
        }

        return sum
    }

    fun part1(input: List<String>): Int {
        return sumPrNr(input)
    }

    fun part2(input: List<String>): Int {
        return 2
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)

    val input = readInput("Day03")
    part1(input).println()
//    part2(input).println()
}
