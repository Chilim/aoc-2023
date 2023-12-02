enum class Color(val value: String) {
    BLUE("blue"),
    RED("red"),
    GREEN("green")
}

val colorsInBag = mapOf(
    Color.BLUE to 14,
    Color.RED to 12,
    Color.GREEN to 13
)

fun calcCubeByColor(color: Color, segment: String) = segment.split(",")
    .filter { it.contains(color.value) }
    .sumOf { it.trim().split(" ")[0].toInt() }

fun splitBySet(input: String) = input.split(";")

fun getGameId(input: String) = input.split(" ")[1].toInt()


fun exceedsLimit(segments: List<String>, color: Color, limit: Int): Boolean {
    for (segment in segments) {
        val num = calcCubeByColor(color, segment)
        if (num > limit) {
            return true
        }
    }
    return false
}

fun parseGameData(line: String): Pair<String, List<String>> {
    val (gameNumber, cubes) = line.split(":", limit = 2)
    val segments = splitBySet(cubes)
    return Pair(gameNumber, segments)
}

fun processGame(gameData: Pair<String, List<String>>): Int {
    val (gameNumber, segments) = gameData
    for ((color, limit) in colorsInBag) {
        if (exceedsLimit(segments, color, limit)) {
            return -1
        }
    }
    return getGameId(gameNumber)
}


fun main() {

    fun part1(input: List<String>): Int {
        val ids = mutableSetOf<Int>()

        input.forEach { line ->
            val gameData = parseGameData(line)
            val gameId = processGame(gameData)
            if (gameId != -1) ids.add(gameId)
        }

        return ids.sum()
    }

//    fun part2(input: List<String>): Int {
//    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)

    val input = readInput("Day02")
    part1(input).println()
//    part2(input).println()
}
