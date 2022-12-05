import java.io.File
import java.lang.IllegalArgumentException
import java.lang.RuntimeException

fun main(args: Array<String>) {
    val stream = File(args[0]).inputStream()
    var overlappingPairsPart1 = 0
    var overlappingPairsPart2 = 0
    stream.bufferedReader().useLines { lines -> lines.forEach { line -> run {
        val pairs = line.split(",")
        if (pairs.size != 2) throw RuntimeException("Invalid line: $line")
        val one = parseInterval(pairs[0])
        val two = parseInterval(pairs[1])
        if ((one.from >= two.from && one.to <= two.to) || (two.from >= one.from && two.to <= one.to)) {
            overlappingPairsPart1++
        }

        if (one.to >= two.from && one.from <= two.to) {
            overlappingPairsPart2++
        }
    } } }
    println("Fully Overlapping pairs: $overlappingPairsPart1")
    println("Partially Overlapping pairs: $overlappingPairsPart2")
}

data class Interval(
    val from: Int,
    val to: Int
)

fun parseInterval(input: String): Interval {
    val items = input.split("-").map { it.toInt() }
    if (items.size != 2) {
        throw IllegalArgumentException("Invalid input: $input")
    }
    return Interval(items[0], items[1])
}