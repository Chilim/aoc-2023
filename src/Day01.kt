
val spelledOutDigits = mapOf(
    "one" to 1, "two" to 2, "three" to 3, "four" to 4,
    "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9
)

fun isNumericChar(c: Char) = c in "otfsen"


val DEFAULT_PAIR = Pair(0, 0)

fun getNumberAndShift(input: String): Pair<Int, Int> {
    spelledOutDigits.forEach { (word, value) ->
        if (input.startsWith(word)) return Pair(value, word.length)
    }
    return DEFAULT_PAIR
}

fun extractFirstDigit(input: String): Int {
    var index = 0
    while (index < input.length) {
        if (isNumericChar(input[index])) {
            val pair = getNumberAndShift(input.substring(index))
            if (pair.first != 0) return pair.first
        } else if (input[index].isDigit()) {
            return input[index].digitToInt()
        }
        index++
    }
    return -1
}

fun extractLastDigit(input: String): Int {
    var index = input.length - 1
    while (index >= 0) {
        if (input[index].isDigit()) return input[index].digitToInt()

        for ((word, value) in spelledOutDigits) {
            if (index >= word.length - 1 && input.substring(index - word.length + 1..index) == word) {
                return value
            }
        }
        index -= 1
    }
    return -1
}

fun extractDigits(input: String) = Pair(extractFirstDigit(input), extractLastDigit(input))


fun main() {

    fun digitToInt(input: String): List<Char> {
        val (digits) = input.toList().partition { it.isDigit() }
        return digits
    }

    fun part1(input: List<String>): Int {
        val digits = input.map { digitToInt(it) }
        return digits.sumOf { list ->
            if (list.size >= 2) {
                "${list.first()}${list.last()}".toInt()
            } else {
                "${list.first()}${list.first()}".toInt()
            }
        }
    }

    fun part2(input: List<String>): Int {
        val digits = input.map { extractDigits(it) }
        return digits.sumOf { (first, last) -> "$first$last".toInt() }
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part2(testInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
