import java.io.File
import java.lang.RuntimeException

fun main(args: Array<String>) {
    val fileName = args[0]
    val stream = File(fileName).inputStream()
    var sumPart1 = 0
    var sumPart2 = 0

    val lineSets = mutableListOf<Set<Char>>()
    stream.bufferedReader().useLines { lines -> lines.forEach { line -> run {
        lineSets.add(HashSet(line.map { it }))
        if (lineSets.size == 3) {
            val common = intersection(*lineSets.toTypedArray())
            if (common.size != 1) {
                throw RuntimeException("Only one difference in three rucksacks is acceptable")
            }
            sumPart2 += priority(common.first())
            lineSets.clear()
        }

        val splitIndex = line.length / 2
        val first = HashSet(line.substring(0, splitIndex).map { it })
        val second = HashSet(line.substring(splitIndex).map {it} )
        val common = intersection(first, second)
        if (common.size != 1) {
            throw RuntimeException("Only one difference is acceptable")
        }
        val char = common.first()
        sumPart1 += priority(char)
    } } }
    println("Answer to part 1: $sumPart1")
    println("Answer to part 2: $sumPart2")
}

fun intersection(vararg args: Set<Char>): Set<Char> {
    if (args.isEmpty()) {
        return emptySet()
    }
    val work = HashSet(args[0])
    for (set in args.asList().subList(1, args.size)) {
        work.retainAll(set)
    }
    return work
}

fun priority(char: Char) = if (char.isUpperCase()) char.code - 65 + 27 else char.code - 97 + 1