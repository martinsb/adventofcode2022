import java.io.File
import java.lang.RuntimeException

val matrixPart1 = mapOf(
    "A X" to 4,
    "A Y" to 8,
    "A Z" to 3,
    "B X" to 1,
    "B Y" to 5,
    "B Z" to 9,
    "C X" to 7,
    "C Y" to 2,
    "C Z" to 6,
)

val matrixPart2 = mapOf(
    "A X" to 3,
    "A Y" to 4,
    "A Z" to 8,
    "B X" to 1,
    "B Y" to 5,
    "B Z" to 9,
    "C X" to 2,
    "C Y" to 6,
    "C Z" to 7,
)

fun main() {
    val stream = File("inputs/02.txt").inputStream()
    var totalPart1 = 0
    var totalPart2 = 0
    stream.bufferedReader().useLines { lines ->
        lines.forEach { line ->
            run {
                if (!matrixPart1.containsKey(line)) {
                    throw RuntimeException("Unknown move, part 1: $line")
                }
                totalPart1 += matrixPart1[line]!!

                if (!matrixPart2.containsKey(line)) {
                    throw RuntimeException("Unknown move, part 2: $line")
                }
                totalPart2 += matrixPart2[line]!!
            }
        }
    }
    println(totalPart1)
    println(totalPart2)
}