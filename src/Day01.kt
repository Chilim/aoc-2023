
fun isDigitLiteral (num: String) = { input: String -> input.length >= num.length  && input.slice(num.indices) == num};

fun isNumericChar(c: Char) = c in "otfsen"


val DEFAULT_PAIR = Pair(0, 0);

fun getNumberAndShift(input: String): Pair<Int, Int> {
    return when(input[0]) {
        'o' -> {
            if (isDigitLiteral("one")(input)) {
                Pair(1, "one".length)
            } else {
                DEFAULT_PAIR
            }
        }
        't' -> {
            if (isDigitLiteral("two")(input)) {
                Pair(2, "two".length)
            } else if (isDigitLiteral("three")(input)) {
                Pair(3, "three".length)
            } else {
                DEFAULT_PAIR
            }
        }
        'f' -> {
            if (isDigitLiteral("four")(input)) {
                Pair(4, "four".length)
            } else if (isDigitLiteral("five")(input)) {
                Pair(5, "five".length)
            } else {
                DEFAULT_PAIR
            }
        }
        's' -> {
            if (isDigitLiteral("six")(input)) {
                Pair(6, "six".length)
            } else if (isDigitLiteral("seven")(input)) {
                Pair(7, "seven".length)
            } else {
                DEFAULT_PAIR
            }
        }
        'e' -> {
            if (isDigitLiteral("eight")(input)) {
                Pair(8, "eight".length)
            } else {
                DEFAULT_PAIR
            }
        }

        else -> {
            if (isDigitLiteral("nine")(input)) {
                Pair(9, "nine".length)
            } else {
                DEFAULT_PAIR
            }
        }
    }
}


fun extractDigits(input: String): Pair<Int, Int> {
    var firstDigit = -1;
    var lastDigit = -1;
    var index = 0;

    while (index < input.length && firstDigit == -1) {
        if (isNumericChar(input[index])) {
            val pair = getNumberAndShift(input.substring(index))
            if (pair.first != 0) {
                firstDigit = pair.first;
            } else {
                index += 1;
            }
        } else if (input[index].isDigit()) {
            firstDigit = input[index].digitToInt();
        } else {
            index +=1;
        }
    }

    index = input.length - 1;

    while (index >= 0 && lastDigit == -1) {
        if (isNumericChar(input[index])) {
            val pair = getNumberAndShift(input.substring(index))
            if (pair.first != 0) {
                lastDigit = pair.first;
            } else {
                index -= 1;
            }
        } else if (input[index].isDigit()) {
            lastDigit = input[index].digitToInt();
        } else {
            index -=1;
        }
    }

    return Pair(firstDigit, lastDigit);
}


fun main() {

    fun digitToInt(input: String): List<Char> {
        val (digits) = input.toList().partition { it.isDigit() }
        return digits;
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
        return digits.sumOf {"${it.first}${it.second}".toInt()}
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
