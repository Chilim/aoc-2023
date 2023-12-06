fun main() {

    fun calcExponential(list: Set<String>) =
        when {
            list.isEmpty() -> 0
            list.size == 1 -> 1
            else -> 1.shl(list.size - 1)
        }

    fun parseToCard(line: String, index: Int): Triple<Int, List<Int>, List<Int>> {
        val (_, cardNumbers) = line.split(": ")
        val (winningNumbers, playerNumbers) = cardNumbers.split(" | ")
            .map { it.split(" ").filter(String::isNotEmpty).map(String::toInt) }
        return Triple(index + 1, winningNumbers, playerNumbers)
    }

    fun sumMatchesTask1(input: List<String>): Int {
        var sum = 0
        input.forEach { line ->
            val (_, cardNumbers) = line.split(":");
            val (winningNumbers, allNumbers) = cardNumbers.split("|")
                .map { set -> set.trim().split("\\s".toRegex()).filter { number -> number.isNotEmpty() } }
            val matches = allNumbers.intersect(winningNumbers.toSet())
            sum += calcExponential(matches)
        }
        return sum
    }


    fun sumMatchesTask2(input: List<String>): Int {
        val cards = input.mapIndexed { index, it -> parseToCard(it, index) }
        val copiesOfCardIdMap = cards.associate { it.first to 1 }.toMutableMap()

        cards.forEach {
            val (currentCardId, winningNumbers, playerNumbers) = it
            val matchedSize = winningNumbers.intersect(playerNumbers.toSet()).size
            val copyRange = (currentCardId + 1)..(currentCardId + matchedSize)
            copyRange.forEach { nextCardId ->
                copiesOfCardIdMap[nextCardId] = copiesOfCardIdMap[nextCardId]!! + copiesOfCardIdMap[currentCardId]!!
            }
        }

        return copiesOfCardIdMap.values.sum()
    }

    fun part1(input: List<String>): Int {
        return sumMatchesTask1(input)
    }

    fun part2(input: List<String>): Int {
        return sumMatchesTask2(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
