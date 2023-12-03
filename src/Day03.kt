const val dotSymbol = '.'

val directions =
    listOf(
        Pair(-1, -1),
        Pair(-1, 0),
        Pair(-1, 1),
        Pair(0, -1),
        Pair(0, 1),
        Pair(1, -1),
        Pair(1, 0),
        Pair(1, 1)
    )

fun main() {

    fun isCharSymbol(char: Char) = !char.isDigit() && char != dotSymbol

    fun extractNumberAndNewIndex(line: String, colIndex: Int): Pair<String, Int> {
        val startIndex = line.take(colIndex).takeLastWhile { it.isDigit() }.length
        val endIndex = line.drop(colIndex).takeWhile { it.isDigit() }.length

        return line.substring(colIndex - startIndex, colIndex + endIndex) to colIndex + endIndex
    }


    fun isAdjacentToSymbol(input: List<String>, rowIdx: Int, colIdx: Int): Boolean {
        return directions.any { (x, y) ->
            val newRow = rowIdx + x
            val newCol = colIdx + y
            input.getOrNull(newRow)?.getOrNull(newCol)?.let { isCharSymbol(it) } == true
        }
    }

    fun sumPrNr(input: List<String>): Int {
        var sum = 0

        for (rowIdx in input.indices) {
            var colIdx = 0
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
