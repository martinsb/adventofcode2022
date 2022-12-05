import java.io.File

fun main(args: Array<String>) {
    val stream = File("inputs/01.txt").inputStream()
    var sum = 0
    val top = mutableListOf<Int>()
    stream.bufferedReader().useLines { lines ->
        lines.forEach { line ->
            run {
                if (line.isNotBlank()) {
                    val c = line.toInt(10)
                    sum += c
                } else {
                    top.add(sum)
                    sum = 0
                }
            }
        }
    }
    top.sortDescending()
    val top3Sum = top.subList(0, 3).sum()
    val topCalories = top[0]
    println("Elf with top calories has $topCalories calories")
    println("Top 3 elves have $top3Sum calories in total")
}