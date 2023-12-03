const val dotSymbol = '.'
const val gearSymbol = '*';

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

    fun isGear (char: Char) = char == gearSymbol

    fun getNumberStartPosition(input: List<String>, rowIndex: Int, colIndex: Int): Pair<Int, Int> {
        val startOfNumberCol = generateSequence(colIndex) { it - 1 }
            .takeWhile { col -> input[rowIndex].getOrNull(col)?.isDigit() == true }
            .last()

        return Pair(rowIndex, startOfNumberCol)
    }


    fun extractAdjacentPrNrs(input: List<String>, rowIndex: Int, colIndex: Int): List<Int> {
        val partNumbersWithPositions = mutableSetOf<Pair<Int, Int>>() // Set to store positions of the start of part numbers
        val partNumbers = mutableListOf<Int>()

        for ((x, y) in directions) {
            val newRowIndex = rowIndex + x
            val newColIndex = colIndex + y
            val candidate = input.getOrNull(newRowIndex)?.getOrNull(newColIndex)

            if (candidate != null && candidate.isDigit()) {

                val position = getNumberStartPosition(input, newRowIndex, newColIndex)
                val startOfNumberCol = position.second

                if (position !in partNumbersWithPositions) {
                    val (number, _) = extractNumberAndNewIndex(input[newRowIndex], startOfNumberCol)
                    partNumbersWithPositions.add(position)
                    partNumbers.add(number.toInt())
                }
            }
        }

        return partNumbers
    }


    fun multiplyAdjacentPrNrs(input: List<String>, rowIndex: Int, colIndex: Int): Int {
        val partNumbers = extractAdjacentPrNrs(input, rowIndex, colIndex)
        if (partNumbers.size < 2) return 0
        return partNumbers[0] * partNumbers[1]
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

    fun sumGearRatios(input: List<String>): Int {
        var sum = 0;

        for (rowIndex in input.indices) {
            for (colIndex in input[rowIndex].indices) {
                if (isGear(input[rowIndex][colIndex])) {
                    sum += multiplyAdjacentPrNrs(input, rowIndex, colIndex)
                }
            }
        }

        return sum
    }

    fun part1(input: List<String>): Int {
        return sumPrNr(input)
    }

    fun part2(input: List<String>): Int {
        return sumGearRatios(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
//    check(part2(testInput) == 467835)

    val input = readInput("Day03")
//    part1(input).println()
    part2(input).println()
}
