

fun main() {

    fun part1(input: List<String>): Int {
        return 1
    }

    fun part2(input: List<String>): Int {
        return 2
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day#_test")
    check(part2(testInput) == 0)

    val input = readInput("Day#")
    part1(input).println()
    part2(input).println()
}
